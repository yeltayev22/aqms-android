package kz.yeltayev.aqms.module.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewMainBinding
import kz.yeltayev.aqms.module.live.LiveFragment
import kz.yeltayev.aqms.module.profile.ProfileFragment
import kz.yeltayev.aqms.module.statistics.StatisticsFragment
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {

    private val mainViewModel: MainViewModel by viewModel()

    private val fragmentManager = supportFragmentManager
    private val liveFragment = LiveFragment()
    private val statisticsFragment = StatisticsFragment()
    private val profileFragment = ProfileFragment()

    private var activeFragment: Fragment = liveFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ViewMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.view_main
            )

        binding.vm = mainViewModel

        binding.bottomNavigationBar.setTabSelectedListener(this)

        fragmentManager.beginTransaction().add(R.id.container, profileFragment).hide(profileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.container, statisticsFragment).hide(statisticsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.container, liveFragment).commit();

        fragmentManager.beginTransaction()
    }

    override fun onTabReselected(position: Int) {
    }

    override fun onTabUnselected(position: Int) {
    }

    override fun onTabSelected(position: Int) {
        when (position) {
            0 -> {
                fragmentManager.beginTransaction().hide(activeFragment).show(liveFragment).commit()
                activeFragment = liveFragment
            }
            1 -> {
                fragmentManager.beginTransaction().hide(activeFragment).show(statisticsFragment).commit()
                activeFragment = statisticsFragment
            }
            2 -> {
                fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                activeFragment = profileFragment
            }
        }
    }
}
