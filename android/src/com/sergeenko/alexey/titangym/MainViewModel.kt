package com.sergeenko.alexey.titangym

import androidx.lifecycle.ViewModel
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Player
import com.sergeenko.alexey.titangym.fragments.GameRequestHandler
import org.koin.android.ext.android.inject

class MainViewModel(
    val game: GameRequestHandler,
    val player: String
) : ViewModel() {

    val myGdxGame: MyGdxGame = MyGdxGame(game, player)

    val gameObserver = game.state


}