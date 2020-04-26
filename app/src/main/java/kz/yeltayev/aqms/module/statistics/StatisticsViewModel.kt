package kz.yeltayev.aqms.module.statistics

import androidx.core.os.bundleOf
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.api.ApiServiceModule
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel
import timber.log.Timber

class StatisticsViewModel : ViewModel() {
    lateinit var navController: NavController

    private val serviceModule = ApiServiceModule()
    private val disposable = CompositeDisposable()

    val isLoading = ObservableBoolean()
    val places = ObservableField<List<PlaceUiModel>>()

    init {
        fetchMyPlaces()
    }

    private fun fetchMyPlaces() {
        isLoading.set(true)
        val placeService = serviceModule.getPlaceService()
        disposable.add(
            placeService.fetchPlaces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { response ->
                    val placeUiList = mutableListOf<PlaceUiModel>()
                    val result = response.body()
                    var index = 1
                    result?.sortedByDescending { it.aqi }?.filter { place ->
                        place.accessCode.isEmpty()
                    }?.forEach { item ->
                        placeUiList.add(PlaceUiModel(item, index++))
                    }

                    this.places.set(placeUiList)

                    isLoading.set(false)
                }
                .doOnError { error ->
                    isLoading.set(false)
                    Timber.d("yeltayev22 $error")
                }
                .subscribe()
        )
    }

    fun onPlaceClicked(placeUiModel: PlaceUiModel?) {
        val bundle = bundleOf("placeUiModel" to placeUiModel)
        navController.navigate(R.id.place_dest, bundle)
    }

}