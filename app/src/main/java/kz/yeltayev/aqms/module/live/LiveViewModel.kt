package kz.yeltayev.aqms.module.live

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.yeltayev.aqms.api.ApiServiceModule
import kz.yeltayev.aqms.model.Place
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel
import kz.yeltayev.aqms.utils.ResourceProvider
import java.math.BigDecimal

class LiveViewModel(
    private val res: ResourceProvider
) : ViewModel() {

    private val serviceModule = ApiServiceModule()
    private val disposable = CompositeDisposable()

    val isLoading = ObservableBoolean()
    val places = ObservableField<List<PlaceUiModel>>()

    init {

        isLoading.set(true)
        val placeService = serviceModule.getPlaceService()
        disposable.add(placeService.fetchPlaces()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                val places = mutableListOf<Place>()
                places.add(
                    Place(
                        1,
                        BigDecimal.ONE,
                        BigDecimal.ONE,
                        "Nur-Sultan",
                        "Kazakhstan",
                        21
                    )
                )

                places.add(
                    Place(
                        1,
                        BigDecimal.ONE,
                        BigDecimal.ONE,
                        "Almaty",
                        "Kazakhstan",
                        158
                    )
                )
                val placeUiList = mutableListOf<PlaceUiModel>()

                places.forEach { item ->
                    placeUiList.add(PlaceUiModel(item, res))
                }
//                response.body()?.forEach { item ->
//                    placeUiList.add(PlaceUiModel(item, res))
//                }

                this.places.set(placeUiList)

                isLoading.set(false)
            })

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}