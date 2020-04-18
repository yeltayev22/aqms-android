package kz.yeltayev.aqms.module.place.tgsinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewTgsInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TgsInfoFragment : Fragment() {

    private val tgsInfoViewModel: TgsInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ViewTgsInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.view_tgs_info, container, false)

        binding.vm = tgsInfoViewModel

        tgsInfoViewModel.navController = findNavController()

        return binding.root
    }
}