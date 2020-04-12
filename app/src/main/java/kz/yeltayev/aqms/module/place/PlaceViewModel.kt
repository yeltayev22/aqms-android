package kz.yeltayev.aqms.module.place

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.yeltayev.aqms.api.WeatherApiServiceModule
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel
import kz.yeltayev.aqms.module.place.widget.WeatherForecastUiModel
import timber.log.Timber
import kotlin.random.Random

class PlaceViewModel : ViewModel() {

    val isLoading = ObservableBoolean()
    val placeUiModel = ObservableField<PlaceUiModel>()

    val dayOne = ObservableField<WeatherForecastUiModel>()
    val dayTwo = ObservableField<WeatherForecastUiModel>()
    val dayThree = ObservableField<WeatherForecastUiModel>()
    val dayFour = ObservableField<WeatherForecastUiModel>()
    val dayFive = ObservableField<WeatherForecastUiModel>()
    val daySix = ObservableField<WeatherForecastUiModel>()
    val daySeven = ObservableField<WeatherForecastUiModel>()

    lateinit var navController: NavController
    private val weatherApiServiceModule = WeatherApiServiceModule()

    private val disposable = CompositeDisposable()

    private fun fetchForecastData() {
        isLoading.set(true)
        val weatherService = weatherApiServiceModule.getWeatherService()
        val place = placeUiModel.get()?.place ?: return
        disposable.add(weatherService.fetchWeekForecast(
            WEATHER_BIT_API_KEY,
            place.latitude,
            place.longitude,
            FORECAST_DAYS_COUNT
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
        fetchForecastData()
    }

    fun goBack() {
        navController.popBackStack()
    }
}

private const val WEATHER_BIT_API_KEY = "a3507d96676d41dabde682c792dc489a"
private const val FORECAST_DAYS_COUNT = 7