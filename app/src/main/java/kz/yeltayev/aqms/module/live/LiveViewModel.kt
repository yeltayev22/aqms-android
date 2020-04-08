package kz.yeltayev.aqms.module.live

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
import kz.yeltayev.aqms.utils.ResourceProvider
import timber.log.Timber

class LiveViewModel(
    private val res: ResourceProvider
) : ViewModel() {

    lateinit var navController: NavController

    private val serviceModule = ApiServiceModule()
    private val disposable = CompositeDisposable()

    val isLoading = ObservableBoolean()
    val places = ObservableField<List<PlaceUiModel>>()

    init {

        isLoading.set(true)
        val placeService = serviceModule.getPlaceService()
        disposable.add(
            placeService.fetchPlaces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { response ->
                    val placeUiList = mutableListOf<PlaceUiModel>()
                    response.body()?.forEach { item ->
                        placeUiList.add(PlaceUiModel(item))
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

    fun onSearchClicked() {
        navController.navigate(R.id.action_search)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}