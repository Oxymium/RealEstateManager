package com.oxymium.realestatemanager

import android.app.Application
import com.oxymium.realestatemanager.di.appModules
import com.oxymium.realestatemanager.di.repositoryModules
import com.oxymium.realestatemanager.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                appModules,
                repositoryModules,
                viewModelModules
            )
        }
    }
}