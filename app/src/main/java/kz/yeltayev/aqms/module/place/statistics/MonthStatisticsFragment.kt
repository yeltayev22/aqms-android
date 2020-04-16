package kz.yeltayev.aqms.module.place.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewMonthStatisticsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MonthStatisticsFragment(
    private val placeId: Long
) : Fragment() {

    private val monthStatisticsViewModel: MonthStatisticsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ViewMonthStatisticsBinding>(
            inflater,
            R.layout.view_month_statistics,
            container,
            false
        )

        binding.vm = monthStatisticsViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        monthStatisticsViewModel.setPlace(placeUiModel)
    }
}