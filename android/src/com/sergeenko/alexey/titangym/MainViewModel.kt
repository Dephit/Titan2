package com.sergeenko.alexey.titangym

import androidx.lifecycle.ViewModel
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Player
import com.sergeenko.alexey.titangym.fragments.GameRequestHandler
import org.koin.android.ext.android.inject

class MainViewModel : ViewModel() {

    public val gameRequestHandler: GameRequestHandler = GameRequestHandler()

    val myGdxGame = MyGdxGame(gameRequestHandler, "")

    val gameObserver = gameRequestHandler.state


}