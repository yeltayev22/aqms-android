package kz.yeltayev.aqms.module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ViewMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.view_main
            )
        binding.vm = mainViewModel

    }
}
