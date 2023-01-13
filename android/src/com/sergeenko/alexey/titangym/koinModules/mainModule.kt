package com.sergeenko.alexey.titangym.koinModules

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.kitegroup.adlibrary.AppOpenManager
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.MyGdxGame
import com.sergeenko.alexey.titangym.AudioManager
import com.sergeenko.alexey.titangym.MainViewModel
import com.sergeenko.alexey.titangym.fragments.GameRequestHandler
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module{

    //TODO add debug mode and ad id
    single { AppOpenManager(androidApplication(), true) }
    single { AudioManager(androidContext()) }
    single { MyGdxGame(get(), "") }
    single { GameRequestHandler() }

    viewModel { parameters -> MainViewModel(player = parameters.get(), game = get()) }


}