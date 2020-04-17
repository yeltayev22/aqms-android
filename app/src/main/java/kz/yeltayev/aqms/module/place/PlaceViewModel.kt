package kz.yeltayev.aqms.module.place

import android.annotation.SuppressLint
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.api.ApiServiceModule
import kz.yeltayev.aqms.api.WeatherApiServiceModule
import kz.yeltayev.aqms.model.Gas
import kz.yeltayev.aqms.model.Weather
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel
import kz.yeltayev.aqms.module.place.widget.BarEntryData
import kz.yeltayev.aqms.module.place.widget.DAYS_IN_WEEK
import kz.yeltayev.aqms.module.place.widget.HOURS_IN_DAY
import kz.yeltayev.aqms.module.place.widget.WeatherForecastUiModel
import kz.yeltayev.aqms.utils.GeneralPreferences
import kz.yeltayev.aqms.utils.ResourceProvider
import kz.yeltayev.aqms.utils.round
import timber.log.Timber
import java.math.BigDecimal
import java.text.SimpleDateFormat
import kotlin.random.Random


class PlaceViewModel(
    private val prefs: GeneralPreferences,
    private val res: ResourceProvider
) : ViewModel() {

    private lateinit var fragment: PlaceFragment

    val isLoading = ObservableBoolean()
    val placeUiModel = ObservableField<PlaceUiModel>()

    var tgs2600 = ObservableField<BigDecimal>()
    var tgs2602 = ObservableField<BigDecimal>()

    val dayOne = ObservableField<WeatherForecastUiModel>()
    val dayTwo = ObservableField<WeatherForecastUiModel>()
    val dayThree = ObservableField<WeatherForecastUiModel>()
    val dayFour = ObservableField<WeatherForecastUiModel>()
    val dayFive = ObservableField<WeatherForecastUiModel>()
    val daySix = ObservableField<WeatherForecastUiModel>()
    val daySeven = ObservableField<WeatherForecastUiModel>()

    val heartIcon = ObservableInt()
    private var saved = false

    lateinit var navController: NavController
    private val weatherApiServiceModule = WeatherApiServiceModule()

    private val disposable = CompositeDisposable()

    /* WEEK STATISTICS */
    val barData = ObservableField<BarData>()
    val maxValue = ObservableInt()
    val xAxis = ObservableField<List<String>>()
    val yAxis = ObservableField<List<Float>>()

    private val serviceModule = ApiServiceModule()

    private val weathers = ObservableField<List<Weather>>()
    private val gases = ObservableField<List<Gas>>()

    val selectedFilter = ObservableField(res.getString(R.string.label_temperature))

    private val filters = arrayOf(
        res.getString(R.string.label_temperature),
        res.getString(R.string.label_pressure),
        res.getString(R.string.label_humidity),
        res.getString(R.string.label_tgs2600),
        res.getString(R.string.label_tgs2602)
    )
    /* WEEK STATISTICS */

    private fun fetchForecastData() {
        isLoading.set(true)
        val weatherService = weatherApiServiceModule.getWeatherService()
        val place = placeUiModel.get()?.place ?: return
        disposable.add(weatherService.fetchWeekForecast(
            WEATHER_BIT_API_KEY,
            place.latitude,
            place.longitude,
            DAYS_IN_WEEK
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { response ->
                isLoading.set(false)
                val weatherForecastList = response.body()

                val weatherForecastUiList = weatherForecastList?.data?.map { item ->
                    val random = Random.nextInt(-10, 10)
                    WeatherForecastUiModel(item, place.aqi + random)
                } ?: return@doOnSuccess

                dayOne.set(weatherForecastUiList[0])
                dayTwo.set(weatherForecastUiList[1])
                dayThree.set(weatherForecastUiList[2])
                dayFour.set(weatherForecastUiList[3])
                dayFive.set(weatherForecastUiList[4])
                daySix.set(weatherForecastUiList[5])
                daySeven.set(weatherForecastUiList[6])
            }
            .doOnError { error ->
                isLoading.set(false)
                Timber.d("yeltayev22 $error")
            }
            .subscribe()
        )
    }

    fun setPlace(placeUiModel: PlaceUiModel) {
        this.placeUiModel.set(placeUiModel)
        tgs2600.set(placeUiModel.place.gas.tgs2600.round())
        tgs2602.set(placeUiModel.place.gas.tgs2602.round())

        fetchForecastData()
        fetchWeatherData()
        fetchGasData()

        checkPrefs()
    }

    private fun checkPrefs() {
        val placeId = placeUiModel.get()?.place?.id ?: return

        val mySavedPlaces = prefs.getMyPlaces()
        Timber.d("yeltayev22 $mySavedPlaces")

        saved = if (mySavedPlaces.contains(placeId.toString())) {
            heartIcon.set(R.drawable.ic_heart_filled)
            true
        } else {
            heartIcon.set(R.drawable.ic_heart_stroke)
            false
        }
    }

    fun onSaveClicked() {
        val placeId = placeUiModel.get()?.place?.id ?: return

        saved = !saved
        if (saved) {
            prefs.addPlace(placeId)
            heartIcon.set(R.drawable.ic_heart_filled)
        } else {
            prefs.removePlace(placeId)
            heartIcon.set(R.drawable.ic_heart_stroke)
        }
    }

    fun onFilterClicked() {
        val context = fragment.context ?: return

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder
            .setSingleChoiceItems(
                filters,
                filters.indexOf(selectedFilter.get())
            ) { dialog, position ->
                selectedFilter.set(filters[position])

                val data = getBarEntryDataByFilter()
                if (data != null) {
                    drawBarChart(data)
                }

                dialog.dismiss()
            }
            .setNegativeButton(res.getString(R.string.label_cancel), null)
            .setTitle(res.getString(R.string.label_select_filter))
            .show()
    }

    private fun fetchWeatherData() {
        val placeService = serviceModule.getPlaceService()
        val placeId = placeUiModel.get()?.place?.id ?: return

        disposable.add(
            placeService.fetchWeatherById(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { response ->
                    val result = response.body() ?: return@doOnSuccess
                    weathers.set(result)

                    val data = getBarEntryDataByFilter()
                    if (data != null) {
                        drawBarChart(data)
                    }
                }
                .doOnError { error ->
                    Timber.d("yeltayev22 $error")
                }
                .subscribe()
        )
    }

    private fun fetchGasData() {
        val placeService = serviceModule.getPlaceService()
        val placeId = placeUiModel.get()?.place?.id ?: return

        disposable.add(
            placeService.fetchGasesById(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { response ->
                    val result = response.body() ?: return@doOnSuccess
                    gases.set(result)
                }
                .doOnError { error ->
                    Timber.d("yeltayev22 $error")
                }
                .subscribe()
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun drawBarChart(result: List<BarEntryData>) {
        val barEntries = mutableListOf<BarEntry>()
        result.sortedByDescending { it.dateTime }

        var index = 0
        val xAxis = mutableListOf<String>()
        val yAxis = mutableListOf<Float>()
        for (i in 0 until DAYS_IN_WEEK * HOURS_IN_DAY step HOURS_IN_DAY) {

            val value = result[i].value
            barEntries.add(
                BarEntry(
                    (index + 1).toFloat(),
                    value
                )
            )
            index = index.inc()

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = dateFormat.parse(result[i].dateTime)
            val dayOfWeekIntFormat = SimpleDateFormat("u")
            val dayOfWeek = dayOfWeekIntFormat.format(date)

            xAxis.add(res.getShortDayName(Integer.parseInt(dayOfWeek)))
            yAxis.add(value)
        }

        this.xAxis.set(xAxis)
        this.yAxis.set(yAxis)

        val maxCount = result.maxBy { it.value }?.value?.toInt()
        if (maxCount != null) {
            maxValue.set(maxCount)
        }

        val barDataSet = BarDataSet(barEntries.sortedWith(compareBy { it.x }), "")
        barDataSet.setDrawValues(false)
        barDataSet.highLightColor = res.getColor(R.color.darkBlue)
        val data = BarData(barDataSet)
        data.barWidth = 0.19f

        if (barEntries.isNotEmpty()) {
            barData.set(data)
        } else {
            barData.set(null)
        }
    }

    private fun getBarEntryDataByFilter(): List<BarEntryData>? {
        val weathers = weathers.get() ?: return null
        val gases = gases.get() ?: return null
        return when (selectedFilter.get()) {
            res.getString(R.string.label_temperature) -> {
                weathers.map { BarEntryData(it.temperature.toFloat(), it.dateTime) }
            }
            res.getString(R.string.label_pressure) -> {
                weathers.map { BarEntryData(it.pressure.toFloat(), it.dateTime) }
            }
            res.getString(R.string.label_humidity) -> {
                weathers.map { BarEntryData(it.humidity.toFloat(), it.dateTime) }
            }
            res.getString(R.string.label_tgs2600) -> {
                gases.map { BarEntryData(it.tgs2600.toFloat(), it.dateTime) }
            }
            res.getString(R.string.label_tgs2602) -> {
                gases.map { BarEntryData(it.tgs2602.toFloat(), it.dateTime) }
            }
            else -> null
        }
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun setFragment(placeFragment: PlaceFragment) {
        fragment = placeFragment
    }
}

private const val WEATHER_BIT_API_KEY = "a3507d96676d41dabde682c792dc489a"