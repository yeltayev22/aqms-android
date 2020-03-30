package kz.yeltayev.aqms.module.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewStatisticsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisticsFragment : Fragment() {

    private val statisticsViewModel: StatisticsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<ViewStatisticsBinding>(inflater, R.layout.view_statistics, container, false)

        binding.vm = statisticsViewModel

        return binding.root
    }
}