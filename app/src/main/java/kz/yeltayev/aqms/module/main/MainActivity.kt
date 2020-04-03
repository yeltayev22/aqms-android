package kz.yeltayev.aqms.module.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewMainBinding
import kz.yeltayev.aqms.module.live.LiveFragment
import kz.yeltayev.aqms.module.profile.ProfileFragment
import kz.yeltayev.aqms.module.statistics.StatisticsFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {

    private val mainViewModel: MainViewModel by viewModel()
    private val navigatorHolder: NavigatorHolder by inject()

    private val fragmentManager = supportFragmentManager
    private val liveFragment = LiveFragment()
    private val statisticsFragment = StatisticsFragment()
    private val profileFragment = ProfileFragment()

    private var activeFragment: Fragment = liveFragment

    private val navigator = object : SupportAppNavigator(this, R.id.container) {
        override fun setupFragmentTransaction(
            command: Command,
            currentFragment: Fragment,
            nextFragment: Fragment,
            fragmentTransaction: FragmentTransaction
        ) {
            fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ViewMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.view_main
            )

        binding.vm = mainViewModel

        binding.bottomNavigationBar.setTabSelectedListener(this)

        fragmentManager.beginTransaction().add(R.id.container, profileFragment)
            .hide(profileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.container, statisticsFragment)
            .hide(statisticsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.container, liveFragment).commit();

        fragmentManager.beginTransaction()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
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
                fragmentManager.beginTransaction().hide(activeFragment).show(statisticsFragment)
                    .commit()
                activeFragment = statisticsFragment
            }
            2 -> {
                fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment)
                    .commit()
                activeFragment = profileFragment
            }
        }
    }
}
