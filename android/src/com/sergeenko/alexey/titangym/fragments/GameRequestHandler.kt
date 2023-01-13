package com.sergeenko.alexey.titangym.fragments

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnClickBooleanCallback
import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.Container
import com.mygdx.game.model.enums.Comp
import com.mygdx.game.model.enums.InventoryType
import com.mygdx.game.model.items.Item
import com.mygdx.game.model.items.OnItemClick
import com.mygdx.game.model.items.perks.PerkItem
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.composeFunctions.*
import com.sergeenko.alexey.titangym.featureGameScreen.models.ComposeState
import com.sergeenko.alexey.titangym.featureGameScreen.models.DialogState
import com.sergeenko.alexey.titangym.featureGameScreen.models.PlayerState
import com.sergeenko.alexey.titangym.featureGameScreen.models.UiState


class GameRequestHandler(): IActivityRequestHandler {

    val state = mutableStateOf<UiState>(UiState())

    override fun showToast(s: String) {
        /*fragment.runOnUiThread {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }*/
    }

    override fun showPlayers(
        playerList: MutableList<CompetitionOpponent>,
        status: Comp,
        runnable: OnClickCallback,
    ) {
        postState(
            state = ComposeState.ShowPlayers(
                playerList = playerList,
                status = status,
            ),
            onClose = { runnable.call(null) }
        )
    }

    override fun showProgressBar(
        title: String,
        onProgressEnd: OnClickCallback,
        onProgressUpdate: OnClickBooleanCallback,
        onClose: OnClickCallback
    ) {
        postState(ComposeState.ShowProgressBar(
            title = title,
            done = { onProgressEnd.call(null) },
            isConditionSatisfied = { onProgressUpdate.isConditionOk() },
        ),
            { onClose.call(null) }
        )
    }

    override fun openOptions() {
        postState(ComposeState.Options)
    }

    override fun showHud(player: Player) {
        postPlayer(PlayerState.HasPlayer(player))
    }

    override fun openStats(player: Player, runnable: Runnable) {

        /*fragment.showComposeView {
            DrawInventory(
                container = player.inventoryManager.equipmentContainer,
                itemsLimit = player.inventoryManager.equipmentContainer.totalCapacity,
                onClose = {
                    hideComposeView(runnable)
                },
                onItemClick = {
                    hideComposeView(runnable)
                }
            )
        }*/
    }

    override fun openPerkMenu(player: Player, pauseGame: Runnable) {
        postState(
            ComposeState.ShowPerkMenu{
                /*val notificationManager = player.notificationManager

                val playerHasPerk = player.inventoryManager?.hasPerk(it as PerkItem?) == true
                val playerCanBuyPerk = (it as PerkItem?)?.canBeBought(player) == true
                val isRequirementSatisfied = it.isRequirementSatisfied(player)

                if(!playerCanBuyPerk){
                    notificationManager?.addMessage(language.youDontHaveEnoughMoney)
                    return@DrawPerkMenu
                }
                if(playerHasPerk){
                    notificationManager?.addMessage(language.youAlreadyHaveThisPerk)
                    return@DrawPerkMenu
                }
                if(!isRequirementSatisfied){
                    notificationManager?.addMessage(language.youCantBuyThisPerkYet)
                    return@DrawPerkMenu
                }

                it.buy(player)
                pauseGame.run()*/
                pauseGame.run()
            }
        ){
            pauseGame.run()
        }
    }

    override fun savePlayer(toString: String?) {
        /*val preferences = context?.getSharedPreferences(context?.packageName, Context.MODE_PRIVATE)
        preferences?.edit()?.putString("PLAYER", toString)?.apply()*/
    }

    override fun showAd(onAdClosed: Runnable?) {

    }

    override fun openInventory(
        player: Player,
        runnable: Runnable,
        onItemClicked: OnItemClick
    ) {
        postState(
            ComposeState.OpenInventory(
                onItemClicked = { showItemDialog(it, onItemClicked) },
            ),
            runnable::run
        )
    }

    override fun openRefrigerator(player: Player, onItemUse: OnItemClick,onClose: Runnable) {
        postState(
            ComposeState
                .OpenRefrigerator(
                    onItemUse = {
                        showItemDialog(it, onItemUse)
                    },
                ),
            onClose = onClose::run
        )
    }

    override fun openShowBuyMenu(
        type: InventoryType,
        inventoryContainer: Container,
        shopContainer: Container,
        onBuyRunnable: OnItemClick,
        onCancel: Runnable
    ) {
        postState(
            ComposeState.OpenBuyingMenu(
                type = type,
                {
                    showItemDialog(it, onBuyRunnable)
                }
            )
        ){
            onCancel.run()
        }
    }

    override fun showDialog(
        title: String,
        subtitle: String,
        onClose: OnClickCallback,
        onAgree: OnClickCallback
    ){
        postDialogState(
            DialogState.ShowDialog(
                title,
                subtitle,
                onAgree = {
                    postDialogState(DialogState.None)
                    onAgree.call(null)
                },
                onClose = {
                    postDialogState(DialogState.None)
                    onClose.call(null)
                }
            )
        )
    }

    override fun showDialog(
        title: String,
        subtitle: String,
        agreeText: String,
        closeText: String,
        onClose: OnClickCallback,
        onAgree: OnClickCallback
    ) {
    }

    override fun showNextSetMenu(
        player: Player,
        currentSet: Int,
        playerList: MutableList<CompetitionOpponent>,
        onFirstClick: OnClickCallback,
        onClose: OnClickCallback
    ) {
        postState(
            ComposeState.ShowCompetitionTable(
                currentSet, playerList, {
                    onFirstClick.call(null)
                }
            )
        ){
            onClose.call(null)
        }
    }


    fun postState(state: ComposeState, onClose: () -> Unit = {}) {
        this.state.value = this.state.value.copy(composeState = state, onClose = { postNone { onClose() } })
    }

    fun postPlayer(state: PlayerState) {
        this.state.value = this.state.value.copy(playerState = state)
    }

    fun postDialogState(state: DialogState) {
        this.state.value = this.state.value.copy(dialogState = state)
    }

    private fun postNone(function: () -> Unit) {
        function()
        postState(ComposeState.None)
    }

    private fun showItemDialog(it: Item, onItemClicked: OnItemClick) {
        postDialogState(DialogState.ShowItemDialog(
            item = it,
            onAgree = {
                postDialogState(DialogState.None)
                onItemClicked.onClick(it)
            },
            onClose = {
                postDialogState(DialogState.None)
            }
        ))
    }

}

