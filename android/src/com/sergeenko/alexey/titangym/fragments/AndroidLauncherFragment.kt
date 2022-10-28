package com.sergeenko.alexey.titangym.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.Language
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.interfaces.OnClickBooleanCallback
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.Container
import com.mygdx.game.model.enums.Comp
import com.mygdx.game.model.items.Item
import com.mygdx.game.model.items.perks.PerkItem
import com.sergeenko.alexey.titangym.*
import com.sergeenko.alexey.titangym.composeFunctions.*
import com.sergeenko.alexey.titangym.databinding.MainActivityBinding
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("Registered")
class AndroidLauncherFragment : AndroidFragmentApplication(), IActivityRequestHandler {
    private var gameLayout: RelativeLayout? = null
    private lateinit var binding: MainActivityBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
        val config = AndroidApplicationConfiguration()
        gameLayout = createGameLayout(config)
        launchGame()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainActivityBinding.inflate(inflater)
        return binding.root
    }

    private fun setBinding() {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        }
        binding.dialogView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
//            dialog()
        }
    }

    private fun launchGame() {
        binding.container.addView(gameLayout)
        showComposeView()
    }

    private fun createGameLayout(config: AndroidApplicationConfiguration): RelativeLayout {
        val layout = RelativeLayout(context)
        activity?.window?.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        }
        val gameView = initializeForView(MyGdxGame(this, arguments?.getString("PLAYER")), config)
        layout.addView(gameView)
        return layout
    }

    override fun showToast(s: String) {
        runOnUiThread {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showComposeView(function: @Composable () -> Unit = {}) {
        binding.composeView.apply {
            setContent {
                MaterialTheme {
                    function()
                }
            }
            post {
                setVisible()
            }
        }
    }


    fun dialog(){
        binding.dialogView.apply {
            setContent {
                MaterialTheme {

                }
            }
        }

    }

    override fun showPlayers(
        playerList: MutableList<CompetitionOpponent>,
        status: Comp,
        runnable: OnClickCallback?,
    ) = showComposeView{
        PlayerList(playerList,status) {
            binding.composeView.setGone()
            runnable?.call(it)
        }
    }

    override fun showProgressBar(
        title: String,
        onProgressEnd: OnClickCallback,
        onProgressUpdate: OnClickBooleanCallback,
        onClose: OnClickCallback
    ) {
        var work = true
        viewModel.resetProgress()
        lifecycleScope.launch {
            while (viewModel.progress.value < 1f && work){
                viewModel.updateProgress(0.1f)
                work = onProgressUpdate.isConditionOk
                withContext(Main){
                    showComposeView {
                        ProgressBar(
                            title = title,
                            onProgressEnd = onProgressEnd,
                            onClose = {
                                work = false
                                binding.composeView.setGone()
                                onClose.call(it)
                            },
                            state = viewModel.progress)
                    }
                }
                delay(1000)
            }
            binding.composeView.setGone()
            onProgressEnd.call(null)
            onClose.call(null)
        }
    }

    override fun openOptions() {
        postRunnable {
            findNavController()
                .navigate(
                    R.id.action_androidLauncherFragment_to_optionsFragment
                )
        }
    }

    override fun showHud(player: Player) {
        binding.hudView.apply {
            setContent {
                HudBar(requireContext().assets, player)
            }
        }
    }

    override fun openStats(player: Player, runnable: Runnable) {
        showComposeView {
            DrawInventory(
                getAssetManager(),
                player.inventoryManager.equipmentContainer,
                {
                    binding.composeView.setGone()
                    runnable.run()
                }
            ) {
                binding.composeView.setGone()
                runnable.run()
            }
        }
    }

    override fun openPerkMenu(player: Player?, pauseGame: Runnable?) {
        showComposeView {
            DrawPerkMenu(getAssetManager(), player,
                {
                    if(player?.inventoryManager?.hasPerk(it as PerkItem?) == false && (it as PerkItem?)?.canBeBought(player) == true){
                        if(it.isRequirementSatisfied(player)) {
                            showDialog(
                                it,
                                onAgree = {
                                    it.buy(player)
                                },
                                onClose = {
                                    pauseGame?.run()
                                }
                            )
                        }else{
                            player.notificationManager?.addMessage(language.youCantBuyThisPerkYet)
                        }
                    }else if((it as PerkItem?)?.canBeBought(player) == false) {
                        player?.notificationManager?.addMessage(language.youDontHaveEnoughMoney)
                    } else{
                        player?.notificationManager?.addMessage(language.youAlreadyHaveThisPerk)
                    }
                }
            ) {
                binding.composeView.setGone()
                pauseGame?.run()
            }
        }
    }

    override fun savePlayer(toString: String?) {
        val preferences = context?.getSharedPreferences(context!!.packageName, Context.MODE_PRIVATE)
        preferences?.edit()?.putString("PLAYER", toString)?.apply()
    }


    override fun openInventory(player: Player, runnable: Runnable) {
        showComposeView {
            DrawInventory(
                getAssetManager(),
                player.inventoryManager.inventory,
                {
                    showDialog(
                        it,
                        onAgree = {
                            it.onUse(player)
                            player.inventoryManager.inventory.removeItem(it)
                        }
                    ){
                        runnable.run()
                    }
                }
            ) {
                binding.composeView.setGone()
                runnable.run()
            }
        }
    }

    override fun openRefrigerator(player: Player, onClose: Runnable?) {
        showComposeView {
            DrawInventory(
                getAssetManager(),
                player.inventoryManager.refrigerator,
                {
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
                binding.composeView.setGone()
                onClose?.run()
            }
        }
    }

    override fun openShopByMenu(
        container: Container,
        onBuyRunnable: OnClickCallback,
        runnable: Runnable
    ) {
        showComposeView {
            DrawInventory(
                getAssetManager(),
                container,
                {
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
                binding.composeView.setGone()
                runnable.run()
            }
        }
    }


    override fun showDialog(
        title: String,
        subtitle: String,
        agreeText: String,
        closeText: String,
        onClose: OnClickCallback,
        onAgree: OnClickCallback
    ){
        viewModel.onOpenDialogClicked()
        binding.root.post {
            showComposeView {
                val showDialogState: Boolean by viewModel.showDialog.collectAsState()
                if(showDialogState){
                    AlertDialogSample(
                        title = title,
                        subtitle = subtitle,
                        agreeText = agreeText,
                        closeText = closeText,
                        onClose = {
                            viewModel.onDialogDismiss()
                            binding.composeView.setGone()
                            onClose.call(it)
                        },
                        onAgree = {
                            viewModel.onDialogConfirm()
                            binding.composeView.setGone()
                            onAgree.call(it)
                        },
                        state = viewModel.showDialog
                    )
                }
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
            agreeText = getString(R.string.ok),
            closeText = getString(R.string.close),
            onClose = onClose,
            onAgree = onAgree
        )
    }

    override fun showNextSetMenu(
        player: Player,
        currentSet: Int,
        playerList: MutableList<CompetitionOpponent>,
        onFirstClick: OnClickCallback?,
        onClose: OnClickCallback?
    ) {
        showComposeView{
            CompetitionTable(
                player = player,
                currentSet = currentSet,
                playerList = playerList,
                onFirstClick = onFirstClick,
                onClose = {
                    binding.composeView.setGone()
                    onClose?.call(it)
                }
            ) {
                binding.composeView.setGone()
            }
        }
    }

}

private fun AndroidLauncherFragment.showDialog(item: Item, onAgree: () -> Unit, onClose: () -> Unit?) {
    showDialog(
        title = item.title,
        subtitle = item.description,
        onAgree = OnClickCallback { _ ->
            onAgree()
            onClose()
        },
        onClose = OnClickCallback {
            onClose()
        }
    )
}

