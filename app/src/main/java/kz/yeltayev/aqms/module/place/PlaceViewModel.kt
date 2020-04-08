package kz.yeltayev.aqms.module.place

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel

class PlaceViewModel : ViewModel() {

    lateinit var navController: NavController

    val placeUiModel = ObservableField<PlaceUiModel>()

    fun setPlace(placeUiModel: PlaceUiModel) {
        this.placeUiModel.set(placeUiModel)
    }

    fun goBack() {
        navController.popBackStack()
    }
}