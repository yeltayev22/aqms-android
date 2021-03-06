package kz.yeltayev.aqms.module.place.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewWeekStatisticsBinding
import kz.yeltayev.aqms.module.place.PlaceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeekStatisticsFragment : Fragment() {

    private val placeViewModel: PlaceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ViewWeekStatisticsBinding>(
            inflater,
            R.layout.view_week_statistics,
            container,
            false
        )

        binding.vm = placeViewModel

        return binding.root
    }
}