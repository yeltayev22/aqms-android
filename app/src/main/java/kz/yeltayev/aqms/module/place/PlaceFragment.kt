package kz.yeltayev.aqms.module.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewPlaceBinding
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel
import kz.yeltayev.aqms.module.place.statistics.MonthStatisticsFragment
import kz.yeltayev.aqms.module.place.statistics.WeekStatisticsFragment
import kz.yeltayev.aqms.module.place.widget.TabFragmentAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaceFragment : Fragment() {

    private val placeViewModel: PlaceViewModel by viewModel()

    private lateinit var binding: ViewPlaceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ViewPlaceBinding>(
            inflater,
            R.layout.view_place,
            container,
            false
        )
        this.binding = binding
        binding.vm = placeViewModel

        placeViewModel.navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val placeUiModel = arguments?.get("placeUiModel") as PlaceUiModel
        placeViewModel.setPlace(placeUiModel)

        val tabFragmentAdapter = TabFragmentAdapter(parentFragmentManager)
        tabFragmentAdapter.addFragment(WeekStatisticsFragment(placeUiModel.place.id), "Week")
        tabFragmentAdapter.addFragment(MonthStatisticsFragment(placeUiModel.place.id), "Month")
        binding.viewPager.adapter = tabFragmentAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}