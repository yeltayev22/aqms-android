package kz.yeltayev.aqms.module.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.view_main.*
import kz.yeltayev.aqms.BaseFragment
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.databinding.ViewMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener {

    private val mainViewModel: MainViewModel by viewModel()

    private val backStack = Stack<Int>()
    private val indexToPage = mapOf(0 to R.id.live, 1 to R.id.statistics, 2 to R.id.profile)

    // list of base destination containers
    private val fragments = listOf(
        BaseFragment.newInstance(R.layout.content_live_base, R.id.toolbar_live, R.id.nav_host_live),
        BaseFragment.newInstance(
            R.layout.content_statistics_base,
            R.id.toolbar_statistics,
            R.id.nav_host_statistics
        ),
        BaseFragment.newInstance(
            R.layout.content_profile_base,
            R.id.toolbar_profile,
            R.id.nav_host_profile
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ViewMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.view_main
            )
        binding.vm = mainViewModel

        // setup main view pager
        main_pager.adapter = ViewPagerAdapter()

        main_pager.addOnPageChangeListener(this)
        bottom_navigation_bar.setOnNavigationItemSelectedListener(this)
        bottom_navigation_bar.setOnNavigationItemReselectedListener(this)

        if (backStack.empty()) {
            backStack.push(0)
        }
    }

    inner class ViewPagerAdapter :
        FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

    }

    override fun onBackPressed() {
        // get the current page
        val fragment = fragments[main_pager.currentItem]
        // check if the page navigates up
        val navigatedUp = fragment.onBackPressed()
        // if no fragments were popped
        if (!navigatedUp) {
            if (backStack.size > 1) {
                // remove current position from stack
                backStack.pop()
                // set the next item in stack as current
                main_pager.currentItem = backStack.peek()

            } else super.onBackPressed()
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(page: Int) {
        val itemId = indexToPage[page] ?: R.id.live
        if (bottom_navigation_bar.selectedItemId != itemId) {
            bottom_navigation_bar.selectedItemId = itemId
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = indexToPage.values.indexOf(item.itemId)
        if (main_pager.currentItem != position) {
            setItem(position)
        }
        return true
    }

    private fun setItem(position: Int) {
        main_pager.currentItem = position
        backStack.push(position)
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val position = indexToPage.values.indexOf(item.itemId)
        val fragment = fragments[position]
        fragment.popToRoot()
    }
}
