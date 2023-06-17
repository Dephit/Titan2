package com.sergeenko.alexey.titangym.featureGameScreen.models

import com.sergeenko.alexey.titangym.fragments.*

data class UiState(
    val playerState: PlayerState = PlayerState.None,
    val composeState: ComposeState = ComposeState.None,
    val dialogState: DialogState = DialogState.None,
    val onClose: () -> Unit = {}
)
