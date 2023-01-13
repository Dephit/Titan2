package com.sergeenko.alexey.titangym.featureGameScreen.models

import com.mygdx.game.Player

sealed class PlayerState(){

    class HasPlayer(val player: Player): PlayerState()

    object None: PlayerState()
}