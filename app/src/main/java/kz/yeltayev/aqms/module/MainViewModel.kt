package kz.yeltayev.aqms.module

import androidx.lifecycle.ViewModel
import kz.yeltayev.aqms.utils.ResourceProvider
import ru.terrakok.cicerone.Router

class MainViewModel(
    val router: Router,
    val res: ResourceProvider
) : ViewModel() {

    init {

    }
}