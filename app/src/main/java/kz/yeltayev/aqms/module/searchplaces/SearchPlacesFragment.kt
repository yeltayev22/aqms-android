package kz.yeltayev.aqms.module.searchplaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewSearchPlacesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchPlacesFragment : Fragment() {

    private val searchPlacesViewModel: SearchPlacesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ViewSearchPlacesBinding =
            DataBindingUtil.inflate(inflater, R.layout.view_search_places, container, false)

        binding.vm = searchPlacesViewModel

        return binding.root
    }
}