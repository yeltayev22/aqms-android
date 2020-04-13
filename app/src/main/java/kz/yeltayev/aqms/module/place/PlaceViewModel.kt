package kz.yeltayev.aqms.module.place

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.api.WeatherApiServiceModule
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel
import kz.yeltayev.aqms.module.place.widget.WeatherForecastUiModel
import kz.yeltayev.aqms.utils.GeneralPreferences
import kz.yeltayev.aqms.utils.round
import timber.log.Timber
import java.math.BigDecimal
import kotlin.random.Random

class PlaceViewModel(
    private val prefs: GeneralPreferences
) : ViewModel() {

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
        tgs2600.set(placeUiModel.place.gas.tgs2600.round())
        tgs2602.set(placeUiModel.place.gas.tgs2602.round())

        fetchForecastData()

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

    fun clickHeart() {
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

    fun goBack() {
        navController.popBackStack()
    }
}

private const val WEATHER_BIT_API_KEY = "a3507d96676d41dabde682c792dc489a"
private const val FORECAST_DAYS_COUNT = 7