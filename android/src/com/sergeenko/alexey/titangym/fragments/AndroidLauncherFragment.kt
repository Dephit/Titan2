package com.sergeenko.alexey.titangym.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnClickBooleanCallback
import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.model.items.Item
import com.sergeenko.alexey.titangym.*
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.composeFunctions.*
import com.sergeenko.alexey.titangym.databinding.MainActivityBinding
import com.sergeenko.alexey.titangym.featureGameScreen.models.ComposeState
import com.sergeenko.alexey.titangym.featureGameScreen.models.PlayerState
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Runnable

class AndroidLauncherFragment : AndroidFragmentApplication() {

    val viewModel: MainViewModel by viewModel{ parametersOf(arguments?.getString("PLAYER"))}

    private val binding: MainActivityBinding by lazy {
        view?.let { MainActivityBinding.bind(it) } ?: MainActivityBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        launchGame()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setFullScreenFlags()
        return binding.root
    }

    private fun launchGame() {
        binding.container.removeAllViews()
        binding.container.addView(createGameLayout())
        binding.composeView.apply {
            setContent {
                MaterialTheme {
                    val gameState = viewModel.gameObserver.value
                    val player = (gameState.playerState as? PlayerState.HasPlayer)?.player
                    val dialogState = gameState.dialogState
                    if(player != null){
                        HudBar(
                            player = player,
                            onActiveItemClick = {}
                        )
                        gameState.composeState.let { state ->
                            when (state) {
                                ComposeState.None -> {}
                                ComposeState.OpenStats -> DrawStatsMenu(player = player)
                                is ComposeState.ShowPlayers -> PlayerList(state, gameState.onClose)
                                is ComposeState.OpenRefrigerator -> DrawRefrigerator(player, state, gameState.onClose)
                                is ComposeState.OpenInventory -> DrawPlayerStates(player, state, gameState.onClose)
                                is ComposeState.ShowProgressBar -> DrawProgressBar(state, gameState.onClose)
                                is ComposeState.Options -> { navigateToOptions() }
                                is ComposeState.OpenBuyingMenu -> DrawShopMenu(player, state, gameState.onClose)
                                is ComposeState.ShowCompetitionTable -> CompetitionTable(player = player, state = state, gameState.onClose)
                                is ComposeState.ShowPerkMenu -> DrawPerkMenu(player = player, state, onClose = gameState.onClose)
                            }
                        }
                    }

                    DialogComposable(dialogState)
                }
            }
        }
    }

    private fun navigateToOptions() {
        findNavController()
            .navigate(
                R.id.action_androidLauncherFragment_to_optionsFragment
            )
    }

    private fun createGameLayout(): View {
        return initializeForView(viewModel.myGdxGame, AndroidApplicationConfiguration())
    }

    private fun setFullScreenFlags() {
        activity?.window?.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        }
    }

}



