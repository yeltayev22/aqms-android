package kz.yeltayev.aqms.module.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : Fragment(), OnMapReadyCallback {

    private val mapViewModel: MapViewModel by viewModel()

    private var mapView: MapView? = null

    private var lastCameraPosition: CameraPosition? = null
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<ViewMapBinding>(inflater, R.layout.view_map, container, false)

        binding.vm = mapViewModel
        mapViewModel.navController = findNavController()

        mapView = binding.mapView
        mapView?.getMapAsync(this)

        mapView?.onCreate(savedInstanceState)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        lastCameraPosition = googleMap.cameraPosition
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        mapViewModel.setGoogleMap(googleMap, lastCameraPosition)
    }
}