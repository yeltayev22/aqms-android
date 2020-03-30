package kz.yeltayev.aqms.module.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewLiveBinding
import kz.yeltayev.aqms.databinding.ViewProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<ViewProfileBinding>(inflater, R.layout.view_profile, container, false)

        binding.vm = profileViewModel

        return binding.root
    }
}