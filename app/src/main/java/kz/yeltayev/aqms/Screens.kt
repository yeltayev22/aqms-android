package kz.yeltayev.aqms

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import kz.yeltayev.aqms.module.MainActivity
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}