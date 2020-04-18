package kz.yeltayev.aqms.module.place.aqiinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewAqiInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AqiInfoFragment : Fragment() {

    private val aqiInfoViewModel: AqiInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ViewAqiInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.view_aqi_info, container, false)

        binding.vm = aqiInfoViewModel

        aqiInfoViewModel.navController = findNavController()

        return binding.root
    }
}