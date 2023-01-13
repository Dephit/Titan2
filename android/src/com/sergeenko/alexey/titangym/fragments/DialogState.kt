package com.sergeenko.alexey.titangym.featureGameScreen.models

import com.mygdx.game.model.items.Item

sealed class DialogState {

    class ShowItemDialog(
        val item: Item,
        val onAgree: () -> Unit = {},
        val onClose: () -> Unit = {}
    ): DialogState()

    class ShowDialog(
        val titleRes: String,
        val subtitleRes: String,
        val onClose: () -> Unit = {},
        val onAgree: () -> Unit = {}
    ): DialogState()

    object None : DialogState()
}