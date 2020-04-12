package kz.yeltayev.aqms.module.searchplaces

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

class SearchPlacesViewModel : ViewModel() {

    lateinit var navController: NavController

    val isLoading = ObservableBoolean()
    val filteredPlaces = ObservableField<List<PlaceUiModel>>()

    private val serviceModule = ApiServiceModule()
    private val disposable = CompositeDisposable()
    private val allPlaces = ObservableField<List<PlaceUiModel>>()

    init {
        fetchPlaces()
    }

    private fun fetchPlaces() {
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

                    allPlaces.set(placeUiList)
                    filteredPlaces.set(placeUiList)

                    isLoading.set(false)
                }
                .doOnError { error ->
                    isLoading.set(false)
                    Timber.d("yeltayev22 $error")
                }
                .subscribe()
        )
    }

    fun search(query: String) {
        val allPlaces = allPlaces.get() ?: return
        val filtered = allPlaces.filter { item ->
            item.place.city.contains(query, true) || item.place.country.contains(query, true)
        }

        filteredPlaces.set(filtered)
    }

    fun onPlaceSelected(item: PlaceUiModel) {
        val bundle = bundleOf("placeUiModel" to item)
        navController.navigate(R.id.action_search_places_dest_to_place_dest, bundle)
    }

    fun goBack() {
        navController.popBackStack()
    }
}