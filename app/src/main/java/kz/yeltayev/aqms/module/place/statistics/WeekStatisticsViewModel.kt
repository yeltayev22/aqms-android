package kz.yeltayev.aqms.module.place.statistics

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.api.ApiServiceModule
import kz.yeltayev.aqms.model.Gas
import kz.yeltayev.aqms.model.Weather
import kz.yeltayev.aqms.utils.ResourceProvider
import timber.log.Timber
import java.text.SimpleDateFormat

class WeekStatisticsViewModel(
    private val res: ResourceProvider
) : ViewModel() {

    val barData = ObservableField<BarData>()
    val maxValue = ObservableInt()
    val xAxis = ObservableField<List<String>>()

    private val serviceModule = ApiServiceModule()
    private val disposable = CompositeDisposable()

    private val weathers = ObservableField<List<Weather>>()
    private val gases = ObservableField<List<Gas>>()

    private var placeId = 0L

    private var selectedFilter: Filter = Filter.TEMPERATURE

    private fun fetchWeatherData() {
        val placeService = serviceModule.getPlaceService()
        disposable.add(
            placeService.fetchWeatherById(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { response ->
                    val result = response.body() ?: return@doOnSuccess

                    drawBarChart(result)
                }
                .doOnError { error ->
                    Timber.d("yeltayev22 $error")
                }
                .subscribe()
        )
    }

    private fun fetchGasData() {

    }

    @SuppressLint("SimpleDateFormat")
    private fun drawBarChart(result: List<Weather>) {
        val barEntries = mutableListOf<BarEntry>()
        result.sortedByDescending { it.dateTime }

        var index = 0
        val xAxis = mutableListOf<String>()
        for (i in 0 until DAYS_IN_WEEK * HOURS_IN_DAY step HOURS_IN_DAY) {

            val value = result[i].temperature
            barEntries.add(
                BarEntry(
                    (index + 1).toFloat(),
                    value.toFloat()
                )
            )
            index = index.inc()

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = dateFormat.parse(result[i].dateTime)
            val dayOfWeekNameFormat = SimpleDateFormat("EEEE")
            val day = dayOfWeekNameFormat.format(date)
            val dayOfWeekIntFormat = SimpleDateFormat("u")
            val dayOfWeek = dayOfWeekIntFormat.format(date)

            xAxis.add(res.getShortDayName(Integer.parseInt(dayOfWeek)))
        }

        this.xAxis.set(xAxis)

        val maxCount = result.maxBy { it.temperature }?.temperature?.toInt()
        if (maxCount != null) {
            maxValue.set(maxCount)
        }

        val barDataSet = BarDataSet(barEntries.sortedWith(compareBy { it.x }), "")
        barDataSet.setDrawValues(false)
        barDataSet.highLightColor = res.getColor(R.color.chartHighlight)
        val data = BarData(barDataSet)
        data.barWidth = 0.19f

        if (barEntries.isNotEmpty()) {
            barData.set(data)
        } else {
            barData.set(null)
        }

        if (result.isEmpty()) {
            barData.set(null)
        }
    }

    fun setPlaceId(placeId: Long) {
        this.placeId = placeId

        fetchWeatherData()
        fetchGasData()
    }
}