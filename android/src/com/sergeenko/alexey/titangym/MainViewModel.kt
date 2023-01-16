package com.sergeenko.alexey.titangym

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Player
import com.sergeenko.alexey.titangym.core.PreferenceManager
import com.sergeenko.alexey.titangym.featureGameScreen.models.UiState
import com.sergeenko.alexey.titangym.fragments.GameRequestHandler
import org.koin.android.ext.android.inject

class MainViewModel(
    val game: GameRequestHandler,
    val loadPlayer: Boolean,
    val preferenceManager: PreferenceManager
) : ViewModel() {

    val myGdxGame: MyGdxGame = MyGdxGame(game, if(loadPlayer) preferenceManager.player else "")

    var gameObserver: MutableState<UiState> = game.state


}