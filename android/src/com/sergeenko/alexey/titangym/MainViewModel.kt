package com.sergeenko.alexey.titangym

import androidx.lifecycle.ViewModel
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.Player
import com.sergeenko.alexey.titangym.fragments.GameRequestHandler

class MainViewModel : ViewModel() {

    public val gameRequestHandler: GameRequestHandler = GameRequestHandler()

    val gameObserver = gameRequestHandler.state


}