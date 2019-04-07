package com.sdv.niceapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.sdv.niceapp.koin.appModule
import com.sdv.niceapp.koin.databaseModule
import com.sdv.niceapp.koin.retrofitModule
import com.sdv.niceapp.koin.serviceModule
import com.sdv.niceapp.util.update
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(appModule, retrofitModule, serviceModule, databaseModule)
        }
    }
}