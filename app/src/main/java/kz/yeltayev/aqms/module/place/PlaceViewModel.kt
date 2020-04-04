package kz.yeltayev.aqms.module.place

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel
import ru.terrakok.cicerone.Router

class PlaceViewModel(
    private val router: Router
) : ViewModel() {

    val placeUiModel = ObservableField<PlaceUiModel>()

    fun setPlace(placeUiModel: PlaceUiModel) {
        this.placeUiModel.set(placeUiModel)
    }

    fun goBack() {
        router.exit()
    }
}