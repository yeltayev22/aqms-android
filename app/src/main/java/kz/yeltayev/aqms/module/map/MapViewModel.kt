package kz.yeltayev.aqms.module.map

import androidx.core.os.bundleOf
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
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

    val isPlaceShown = ObservableBoolean(false)
    var placeUiModel = ObservableField<PlaceUiModel>()

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

            val markerOptions = MarkerOptions().position(LatLng(lat, lon))
            val marker = googleMap?.addMarker(markerOptions)
            marker?.tag = item
        }
    }

    fun setGoogleMap(googleMap: GoogleMap, lastCameraPosition: CameraPosition?) {
        this.googleMap = googleMap

        if (lastCameraPosition != null) {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(lastCameraPosition))
        }

        googleMap.setOnMarkerClickListener { marker ->
            placeUiModel.set(marker.tag as PlaceUiModel)
            isPlaceShown.set(true)

            return@setOnMarkerClickListener true
        }

        googleMap.setOnMapClickListener {
            isPlaceShown.set(false)
            placeUiModel.set(null)
        }
        addMarkers(placeUiList)
    }

    fun onPlaceClicked() {
        val placeUiModel = placeUiModel.get() ?: return
        val bundle = bundleOf("placeUiModel" to placeUiModel)
        navController.navigate(R.id.place_dest, bundle)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}