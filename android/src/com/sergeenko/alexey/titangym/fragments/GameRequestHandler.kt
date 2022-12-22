package com.sergeenko.alexey.titangym.fragments

import android.content.Context
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.compose.runtime.*
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnClickBooleanCallback
import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.Container
import com.mygdx.game.model.enums.Comp
import com.mygdx.game.model.items.Item
import com.mygdx.game.model.items.OnItemClick
import com.mygdx.game.model.items.perks.PerkItem
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.composeFunctions.*


class GameRequestHandler(val fragment: ComposeFragmentInterface): IActivityRequestHandler {

    private val context: Context = fragment.requireContext()

    var dialogScreenState = mutableStateOf(false)

    override fun showToast(s: String) {
        fragment.runOnUiThread {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showPlayers(
        playerList: MutableList<CompetitionOpponent>,
        status: Comp,
        runnable: OnClickCallback?,
    ) = fragment.showComposeView{
        PlayerList(playerList,status) {
            fragment.hideComposeView()
            runnable?.call(it)
        }
    }

    override fun showProgressBar(
        title: String,
        onProgressEnd: OnClickCallback,
        onProgressUpdate: OnClickBooleanCallback,
        onClose: OnClickCallback
    ) {
        fragment.showProgressBar(title, onProgressEnd, onProgressUpdate, onClose)
    }

    override fun openOptions() {
        fragment.postRunnable {
            fragment.navController()
                .navigate(
                    R.id.action_androidLauncherFragment_to_optionsFragment
                )
        }
    }

    override fun showHud(player: Player) {
        fragment.showHud(player)
    }

    override fun openStats(player: Player, runnable: Runnable) {
        fragment.showComposeView {
            DrawInventory(
                container = player.inventoryManager.equipmentContainer,
                onClose = {
                    hideComposeView(runnable)
                },
                onItemClick = {
                    hideComposeView(runnable)
                }
            )
        }
    }

    fun hideComposeView(runnable: Runnable? = null){
        fragment.hideComposeView()
        runnable?.run()
    }

    override fun openPerkMenu(player: Player?, pauseGame: Runnable?) {
        fragment.showComposeView {
            DrawPerkMenu(player,
                {
                    val notificationManager = player?.notificationManager

                    val playerHasPerk = player?.inventoryManager?.hasPerk(it as PerkItem?) == true
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

                    showDialog(
                        title = it.title,
                        subtitle = it.description,
                        onAgree = OnClickCallback { _ ->
                            it.buy(player)
                            pauseGame?.run()
                        },
                        onClose = OnClickCallback {
                            pauseGame?.run()
                        }
                    )
                }
            ) {
                hideComposeView()
                pauseGame?.run()
            }
        }
    }

    override fun savePlayer(toString: String?) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences?.edit()?.putString("PLAYER", toString)?.apply()
    }

    override fun showAd(onAdClosed: Runnable?) {
        hideComposeView()
        /*fragment.scope.launch {
            fragment.showLoader()
            Inter.Builder()
                .onAdClosed {
                    onAdClosed?.run()
                    hideComposeView()
                }
                .onAdLoaded {  }
                .onAdPreload { }
                .onAdShowed {  }
                .build()
                .showAd(fragment.requireActivity())
        }*/
    }

    override fun openInventory(
        player: Player,
        runnable: Runnable,
        onItemClicked: OnItemClick
    ) {
        fun onClose(){
            hideComposeView()
            runnable.run()
        }

        val state = player.inventoryManager.inventory.items.toMutableStateList()

        fun onItemClicked(item: Item){
            showDialog(
                item = item,
                onAgree = {
                    onItemClicked.onClick(item)
                }
            ){
                runnable.run()
            }
        }

        fragment.showComposeView {
            DrawPlayerStates(
                player = player,
                container = state,
                onItemClicked = ::onItemClicked,
                onClose = ::onClose
            )
        }
    }

    override fun openRefrigerator(player: Player, onClose: Runnable?) {
        fragment.showComposeView {
            DrawInventory(
                container = player.inventoryManager.refrigerator,
                onItemClick = {
                    showDialog(
                        it,
                        onAgree = {
                            it.onUse(player)
                            player.inventoryManager.refrigerator.removeItem(it)
                        }
                    ){
                        onClose?.run()
                    }
                }
            ) {
                hideComposeView()
                onClose?.run()
            }
        }
    }

    override fun openShopByMenu(
        container: Container,
        onBuyRunnable: OnClickCallback,
        runnable: Runnable
    ) {
        fragment.showComposeView {
            DrawInventory(
                container = container,
                onItemClick = {
                    showDialog(
                        it,
                        onAgree = {
                            onBuyRunnable.call(it)
                        }
                    ){
                        runnable.run()
                    }
                }
            ) {
                hideComposeView()
                runnable.run()
            }
        }
    }




    override fun showDialog(
        title: String,
        subtitle: String,
        onClose: OnClickCallback,
        onAgree: OnClickCallback
    ){
        showDialog(
            title = title,
            subtitle = subtitle,
            agreeText = context.getString(R.string.ok),
            closeText = context.getString(R.string.close),
            onClose = onClose,
            onAgree = onAgree
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
        dialogScreenState.value = true

        fragment.showDialogView {
            if (dialogScreenState.value == true){
                AlertDialogSample(
                    title = title,
                    subtitle = subtitle,
                    agreeText = context.getString(R.string.ok),
                    closeText = context.getString(R.string.close),
                    onAgree = {
                        dialogScreenState.value = false
                        onAgree.call(null)
                    },
                    onClose = {
                        dialogScreenState.value = false
                        //onClose.call(null)
                    },
                    state = dialogScreenState.value
                )
            }else{

            }
        }
    }

    override fun showNextSetMenu(
        player: Player,
        currentSet: Int,
        playerList: MutableList<CompetitionOpponent>,
        onFirstClick: OnClickCallback?,
        onClose: OnClickCallback?
    ) {
        fragment.showComposeView{
            CompetitionTable(
                player = player,
                currentSet = currentSet,
                playerList = playerList,
                onFirstClick = onFirstClick,
                onClose = {
                    hideComposeView()
                    onClose?.call(it)
                }
            ) {
                hideComposeView()
            }
        }
    }

    private fun showDialog(item: Item, onAgree: () -> Unit, onClose: () -> Unit?) {
        showDialog(
            title = item.title,
            subtitle = item.description,
            agreeText = context.getString(R.string.ok),
            closeText = context.getString(R.string.close),
            onClose = {
                onClose()
            },
            onAgree = {
                onAgree()
            }
        )
    }

}

