package com.sergeenko.alexey.titangym.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.compositionContext
import androidx.core.view.doOnAttach
import androidx.navigation.fragment.findNavController
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.sergeenko.alexey.titangym.MainViewModel
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.composeFunctions.*
import com.sergeenko.alexey.titangym.databinding.MainActivityBinding
import com.sergeenko.alexey.titangym.featureGameScreen.models.ComposeState
import com.sergeenko.alexey.titangym.featureGameScreen.models.PlayerState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AndroidLauncherFragment : AndroidFragmentApplication() {

    private val viewModel: MainViewModel by viewModel{
        parametersOf(arguments?.getBoolean(MainMenuFragment.LOAD_PLAYER) ?: false)
    }

    private val binding: MainActivityBinding by lazy {
        view?.let { MainActivityBinding.bind(it) } ?: MainActivityBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val gameState = viewModel.gameObserver.value
                        val dialogState = gameState.dialogState
                        if (gameState.playerState is PlayerState.HasPlayer) {
                            val player = gameState.playerState.player

                            HudBar(
                                player = player,
                                onActiveItemClick = {}
                            )
                            gameState.composeState.let { state ->
                                when (state) {
                                    ComposeState.None -> {}
                                    ComposeState.OpenStats -> DrawStatsMenu(player = player)
                                    is ComposeState.ShowPlayers -> PlayerList(state,
                                        gameState.onClose)
                                    is ComposeState.OpenRefrigerator -> DrawRefrigerator(player,
                                        state,
                                        gameState.onClose)
                                    is ComposeState.OpenInventory -> DrawPlayerStates(player,
                                        state,
                                        gameState.onClose)
                                    is ComposeState.ShowProgressBar -> DrawProgressBar(state,
                                        gameState.onClose)
                                    is ComposeState.Options -> {
                                        navigateToOptions()
                                    }
                                    is ComposeState.OpenBuyingMenu -> DrawShopMenu(player,
                                        state,
                                        gameState.onClose)
                                    is ComposeState.ShowCompetitionTable -> CompetitionTable(player = player,
                                        state = state,
                                        gameState.onClose)
                                    is ComposeState.ShowPerkMenu -> DrawPerkMenu(player = player,
                                        state,
                                        onClose = gameState.onClose)
                                }
                            }
                        }

                        DialogComposable(dialogState)
                }
            }
        }
        binding.container.removeAllViews()
        binding.container.addView(createGameLayout())
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



