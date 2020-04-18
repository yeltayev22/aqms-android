package kz.yeltayev.aqms.module.place.aqiinfo

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class AqiInfoViewModel : ViewModel() {

    lateinit var navController: NavController

    fun goBack() {
        navController.popBackStack()
    }
}