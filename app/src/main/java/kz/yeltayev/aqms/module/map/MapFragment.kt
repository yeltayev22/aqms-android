package kz.yeltayev.aqms.module.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment() {

    private val mapViewModel: MapViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<ViewMapBinding>(inflater, R.layout.view_map, container, false)

        binding.vm = mapViewModel

        return binding.root
    }
}