package com.sergeenko.alexey.titangym.koinModules

import com.kitegroup.adlibrary.AppOpenManager
import com.mygdx.game.MyGdxGame
import com.sergeenko.alexey.titangym.core.AudioManager
import com.sergeenko.alexey.titangym.MainViewModel
import com.sergeenko.alexey.titangym.core.PreferenceManager
import com.sergeenko.alexey.titangym.fragments.GameRequestHandler
import com.sergeenko.alexey.titangym.optionsFeature.viewModels.OptionsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module{

    //TODO add debug mode and ad id
    single { AppOpenManager(androidApplication(), true) }
    single { AudioManager(androidContext(), get()) }
    single { PreferenceManager(androidContext()) }
    factory { MyGdxGame(get(), "") }
    factory { GameRequestHandler(get()) }

    viewModel { parameters -> MainViewModel(loadPlayer = parameters.get(), game = get(), preferenceManager = get()) }
    viewModelOf(::OptionsViewModel)

}