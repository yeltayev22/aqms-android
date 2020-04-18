package kz.yeltayev.aqms.module.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
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
    private lateinit var fragment: MapFragment

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
                    response.body()?.filter { place ->
                        place.accessCode.isEmpty()
                    }?.forEach { item ->
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

            val icon = getIcon(item)
            marker?.setIcon(icon)

            marker?.tag = item
        }
    }

    private fun getIcon(item: PlaceUiModel): BitmapDescriptor? {
        val context = this.fragment.context ?: return null
        when (item.place.aqi) {
            in 1..50 -> {
                return bitmapDescriptorFromVector(context, R.drawable.ic_map_marker_good)
            }
            in 51..100 -> {
                return bitmapDescriptorFromVector(context, R.drawable.ic_map_marker_moderate)
            }
            in 101..150 -> {
                return bitmapDescriptorFromVector(context,R.drawable.ic_map_marker_unhealthy_for_sensitive)
            }
            in 151..200 -> {
                return bitmapDescriptorFromVector(context, R.drawable.ic_map_marker_unhealthy)
            }
            in 201..300 -> {
                return bitmapDescriptorFromVector(context, R.drawable.ic_map_marker_very_unhealthy)
            }
            else -> {
                return bitmapDescriptorFromVector(context, R.drawable.ic_map_marker_hazardous)
            }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    fun setFragment(fragment: MapFragment) {
        this.fragment = fragment
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