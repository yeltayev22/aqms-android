package kz.yeltayev.aqms.module.place.widget

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kz.yeltayev.aqms.module.place.statistics.MonthStatisticsFragment
import kz.yeltayev.aqms.module.place.statistics.WeekStatisticsFragment

class TabFragmentAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            WeekStatisticsFragment()
        } else {
            MonthStatisticsFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Week"
            1 -> "Month"
            else -> null
        }
    }

}