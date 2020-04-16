package kz.yeltayev.aqms

import androidx.multidex.MultiDexApplication
import kz.yeltayev.aqms.api.ApiServiceModule
import kz.yeltayev.aqms.api.WeatherApiServiceModule
import kz.yeltayev.aqms.module.live.LiveViewModel
import kz.yeltayev.aqms.module.main.MainViewModel
import kz.yeltayev.aqms.module.map.MapViewModel
import kz.yeltayev.aqms.module.place.PlaceViewModel
import kz.yeltayev.aqms.module.place.statistics.MonthStatisticsViewModel
import kz.yeltayev.aqms.module.place.statistics.WeekStatisticsViewModel
import kz.yeltayev.aqms.module.searchplaces.SearchPlacesViewModel
import kz.yeltayev.aqms.module.statistics.StatisticsViewModel
import kz.yeltayev.aqms.utils.GeneralPreferences
import kz.yeltayev.aqms.utils.LocalStorageImpl
import kz.yeltayev.aqms.utils.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : MultiDexApplication() {

    private val viewModelModule = module {
        viewModel { MainViewModel(get()) }

        viewModel { LiveViewModel(get(), get()) }
        viewModel { SearchPlacesViewModel() }
        viewModel { PlaceViewModel(get()) }

        viewModel { StatisticsViewModel() }
        viewModel { MapViewModel(get()) }

        viewModel { MonthStatisticsViewModel() }
        viewModel { WeekStatisticsViewModel(get()) }

    }

    private val listOfModules = module {
        single { ApiServiceModule() }
        single { WeatherApiServiceModule() }
        single { ResourceProvider(get()) }
        single { GeneralPreferences(get()) }
        single { LocalStorageImpl(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOfModules)
            modules(viewModelModule)
        }
    }
}