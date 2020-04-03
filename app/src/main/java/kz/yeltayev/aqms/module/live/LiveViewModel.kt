package kz.yeltayev.aqms.module.live

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.yeltayev.aqms.Screens
import kz.yeltayev.aqms.api.ApiServiceModule
import kz.yeltayev.aqms.model.Place
import kz.yeltayev.aqms.module.live.widget.MyPlaceUiModel
import kz.yeltayev.aqms.utils.ResourceProvider
import ru.terrakok.cicerone.Router
import java.math.BigDecimal

class LiveViewModel(
    private val router: Router,
    private val res: ResourceProvider
) : ViewModel() {

    private val serviceModule = ApiServiceModule()
    private val disposable = CompositeDisposable()

    val isLoading = ObservableBoolean()
    val places = ObservableField<List<MyPlaceUiModel>>()

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

                places.add(
                    Place(
                        1,
                        BigDecimal.ONE,
                        BigDecimal.ONE,
                        "Beijing",
                        "China",
                        301
                    )
                )
                val placeUiList = mutableListOf<MyPlaceUiModel>()

                places.forEach { item ->
                    placeUiList.add(MyPlaceUiModel(item))
                }
//                response.body()?.forEach { item ->
//                    placeUiList.add(PlaceUiModel(item, res))
//                }

                this.places.set(placeUiList)

                isLoading.set(false)
            })

    }

    fun onSearchClicked() {
        router.navigateTo(Screens.SearchPlacesScreen())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}