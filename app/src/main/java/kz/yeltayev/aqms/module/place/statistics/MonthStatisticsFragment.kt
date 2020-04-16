package kz.yeltayev.aqms.module.place.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewMonthStatisticsBinding
import kz.yeltayev.aqms.module.place.PlaceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MonthStatisticsFragment : Fragment() {

    private val placeViewModel: PlaceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ViewMonthStatisticsBinding>(
            inflater,
            R.layout.view_month_statistics,
            container,
            true
        )

        binding.vm = placeViewModel

        return binding.root
    }
}