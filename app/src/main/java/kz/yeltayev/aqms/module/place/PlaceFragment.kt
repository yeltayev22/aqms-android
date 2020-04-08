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
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaceFragment(
    val placeUiModel: PlaceUiModel
) : Fragment() {

    private val placeViewModel: PlaceViewModel by viewModel()

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

        binding.vm = placeViewModel

        placeViewModel.navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        placeViewModel.setPlace(placeUiModel)
    }


}