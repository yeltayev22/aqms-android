package kz.yeltayev.aqms.module.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewLiveBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LiveFragment : Fragment() {

    private val liveViewModel: LiveViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<ViewLiveBinding>(inflater, R.layout.view_live, container, false)

        binding.vm = liveViewModel

        return binding.root
    }
}