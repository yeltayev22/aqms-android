package kz.yeltayev.aqms

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import kz.yeltayev.aqms.module.main.MainActivity
import kz.yeltayev.aqms.module.searchplaces.SearchPlacesFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    class SearchPlacesScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return SearchPlacesFragment()
        }
    }
}