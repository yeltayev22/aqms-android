package kz.yeltayev.aqms.module.place.tgsinfo

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class TgsInfoViewModel : ViewModel() {

    lateinit var navController: NavController

    fun goBack() {
        navController.popBackStack()
    }
}