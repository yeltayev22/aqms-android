package kz.yeltayev.aqms.module.live

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
import kz.yeltayev.aqms.utils.GeneralPreferences
import kz.yeltayev.aqms.utils.ResourceProvider
import timber.log.Timber

class LiveViewModel(
    private val res: ResourceProvider,
    private val prefs: GeneralPreferences
) : ViewModel() {

    lateinit var navController: NavController

    private val serviceModule = ApiServiceModule()
    private val disposable = CompositeDisposable()

    val isLoading = ObservableBoolean()
    val places = ObservableField<List<PlaceUiModel>>()
    val noData = ObservableBoolean(false)

    init {
        fetchMyPlaces()
    }

    fun fetchMyPlaces() {
        val myPlaces = prefs.getMyPlaces()

        isLoading.set(true)
        val placeService = serviceModule.getPlaceService()
        disposable.add(
            placeService.fetchPlaces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { response ->
                    val placeUiList = mutableListOf<PlaceUiModel>()
                    response.body()?.forEach { item ->
                        if (myPlaces.contains(item.id.toString())) {
                            placeUiList.add(PlaceUiModel(item))
                        }
                    }

                    if (placeUiList.isNullOrEmpty()) {
                        noData.set(true)
                    }

                    this.places.set(placeUiList)

                    isLoading.set(false)
                }
                .doOnError { error ->
                    noData.set(true)
                    isLoading.set(false)
                    Timber.d("yeltayev22 $error")
                }
                .subscribe()
        )
    }

    fun onSearchClicked() {
        navController.navigate(R.id.action_search)
    }

    fun onPlaceClicked(item: PlaceUiModel) {
        val bundle = bundleOf("placeUiModel" to item)
        navController.navigate(R.id.place_dest, bundle)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}