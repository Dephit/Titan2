package com.sergeenko.alexey.titangym

import android.app.Application
import com.sergeenko.alexey.titangym.koinModules.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            // declare used Android context
            androidContext(applicationContext)
            // declare modules
            modules(mainModule)
        }
    }
}