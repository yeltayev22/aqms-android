package kz.yeltayev.aqms.module.searchplaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.yeltayev.aqms.Screens
import kz.yeltayev.aqms.api.ApiServiceModule
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel
import ru.terrakok.cicerone.Router
import timber.log.Timber

class SearchPlacesViewModel(
    private val router: Router
) : ViewModel() {

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
        router.navigateTo(Screens.PlaceScreen(item))
    }

    fun goBack() {
        router.exit()
    }
}