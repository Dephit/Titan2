package com.sergeenko.alexey.titangym.koinModules

import com.kitegroup.adlibrary.AppOpenManager
import com.sergeenko.alexey.titangym.AudioManager
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module{

    //TODO add debug mode and ad id
    single { AppOpenManager(androidApplication(), true) }
    single { AudioManager(androidContext()) }

}