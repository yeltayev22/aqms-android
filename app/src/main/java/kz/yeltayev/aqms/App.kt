package kz.yeltayev.aqms

import android.app.Application
import kz.yeltayev.aqms.api.GitHubServiceAPI
import kz.yeltayev.aqms.module.MainViewModel
import kz.yeltayev.aqms.utils.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class App : Application() {

    private var cicerone: Cicerone<Router>? = null

    private val navigationModule = module {
        single { cicerone?.router }
        single { cicerone?.navigatorHolder }
    }

    private val viewModelModule = module {
        viewModel { MainViewModel(get(), get()) }
    }

    private val listOfModules = module {
        single { GitHubServiceAPI() }
        single { ResourceProvider(get()) }
    }


    override fun onCreate() {
        super.onCreate()

        initCicerone()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOfModules)
            modules(navigationModule)
            modules(viewModelModule)
        }
    }

    private fun initCicerone() {
        cicerone = Cicerone.create();
    }
}