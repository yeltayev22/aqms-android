package kz.yeltayev.aqms.module.searchplaces

import androidx.lifecycle.ViewModel
import ru.terrakok.cicerone.Router

class SearchPlacesViewModel(
    private val router: Router
) : ViewModel() {

    fun goBack() {
        router.exit()
    }
}