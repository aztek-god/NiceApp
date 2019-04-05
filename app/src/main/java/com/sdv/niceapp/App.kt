package com.sdv.niceapp

import android.app.Application
import com.sdv.niceapp.koin.appModule
import com.sdv.niceapp.koin.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(appModule, retrofitModule)
        }
    }
}