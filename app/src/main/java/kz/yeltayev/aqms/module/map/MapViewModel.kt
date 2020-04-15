package kz.yeltayev.aqms.module.map

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.api.ApiServiceModule
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel
import kz.yeltayev.aqms.utils.ResourceProvider
import timber.log.Timber

class MapViewModel(
    private val res: ResourceProvider
) : ViewModel() {

    lateinit var navController: NavController

    private val serviceModule = ApiServiceModule()
    private val disposable = CompositeDisposable()

    private var googleMap: GoogleMap? = null
    private val placeUiList = mutableListOf<PlaceUiModel>()

    init {
        fetchMyPlaces()
    }

    private fun fetchMyPlaces() {
        val placeService = serviceModule.getPlaceService()
        disposable.add(
            placeService.fetchPlaces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { response ->
                    response.body()?.forEach { item ->
                        placeUiList.add(PlaceUiModel(item))
                    }

                    addMarkers(placeUiList)
                }
                .doOnError { error ->
                    Timber.d("yeltayev22 $error")
                }
                .subscribe()
        )
    }

    private fun addMarkers(placeUiList: List<PlaceUiModel>) {
        googleMap?.clear()

        placeUiList.forEach { item ->
            val lat = item.place.latitude.toDouble()
            val lon = item.place.longitude.toDouble()

            val markerOptions = MarkerOptions().position(LatLng(lat, lon)).title(item.place.city)
                .snippet(item.place.aqi.toString())
            val marker = googleMap?.addMarker(markerOptions)
            marker?.tag = item
        }
    }

    fun setGoogleMap(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.setOnInfoWindowClickListener { marker ->
            val placeUiModel = marker.tag as PlaceUiModel
            onPlaceClicked(placeUiModel)
        }
        addMarkers(placeUiList)
    }

    private fun onPlaceClicked(item: PlaceUiModel) {
        val bundle = bundleOf("placeUiModel" to item)
        navController.navigate(R.id.place_dest, bundle)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}