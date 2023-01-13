package com.sergeenko.alexey.titangym.featureGameScreen.models

import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.Container
import com.mygdx.game.model.enums.Comp
import com.mygdx.game.model.enums.InventoryType
import com.mygdx.game.model.items.Item
import com.mygdx.game.model.items.OnItemClick
import com.mygdx.game.model.items.perks.PerkItem

sealed class ComposeState(){

    class ShowPlayers(
        val playerList: MutableList<CompetitionOpponent>,
        val status: Comp
        ): ComposeState()

    class OpenRefrigerator(val onItemUse: OnItemClick): ComposeState()

    class OpenInventory(val onItemClicked: OnItemClick): ComposeState()

    class ShowProgressBar(
        val title: String,
        val done: () -> Unit,
        val isConditionSatisfied: () -> Boolean
    ): ComposeState()

    class OpenBuyingMenu(
        val type: InventoryType,
        val onBuyRunnable: (Item) -> Unit,
    ): ComposeState()

    class ShowCompetitionTable(
        val currentSet: Int,
        val playerList: MutableList<CompetitionOpponent>,
        val onFirstClick: () -> Unit
    ): ComposeState()

    class ShowPerkMenu(
        val onItemClick: (PerkItem) -> Unit
    ): ComposeState()

    object OpenStats: ComposeState()

    object Options: ComposeState()

    object None: ComposeState()

}