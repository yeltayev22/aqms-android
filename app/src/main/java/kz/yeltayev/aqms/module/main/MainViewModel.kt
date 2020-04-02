package kz.yeltayev.aqms.module.main

import androidx.lifecycle.ViewModel
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.utils.ResourceProvider
import ru.terrakok.cicerone.Router

class MainViewModel(
    val router: Router,
    val res: ResourceProvider
) : ViewModel() {

    val bottomNavigationItems = mutableListOf<BottomNavigationItem>()

    init {
        bottomNavigationItems.add(
            BottomNavigationItem(
                res.getDrawable(R.drawable.ic_live),
                res.getString(R.string.label_live)
            )
        )
        bottomNavigationItems.add(
            BottomNavigationItem(
                res.getDrawable(R.drawable.ic_statistics),
                res.getString(R.string.label_statistics)
            )
        )
        bottomNavigationItems.add(
            BottomNavigationItem(
                res.getDrawable(R.drawable.ic_profile),
                res.getString(R.string.label_profile)
            )
        )
    }
}