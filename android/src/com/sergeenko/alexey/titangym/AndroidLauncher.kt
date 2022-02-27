package com.sergeenko.alexey.titangym

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnCLickCallback
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.CompetitionOpponent.Attempt
import com.mygdx.game.model.enums.Comp
import com.sergeenko.alexey.titangym.composeFunctions.CompetitionTable
import com.sergeenko.alexey.titangym.composeFunctions.PlayerList
import com.sergeenko.alexey.titangym.databinding.MainActivityBinding
import com.sergeenko.alexey.titangym.databinding.NextSetViewBinding
import com.sergeenko.alexey.titangym.databinding.PlayerListBinding
import com.sergeenko.alexey.titangym.items.PlayerComposeItem
import com.sergeenko.alexey.titangym.items.TableHeaderComposeItem
import kotlin.random.Random


@SuppressLint("Registered")
class AndroidLauncherFragment : AndroidFragmentApplication(), IActivityRequestHandler {
    private var gameLayout: RelativeLayout? = null
    private lateinit var binding: MainActivityBinding

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
            setVisible()
        }
    }

    private fun launchGame() {
        binding.container.addView(gameLayout)
    }

    private fun createGameLayout(config: AndroidApplicationConfiguration): RelativeLayout {
        val layout = RelativeLayout(context)
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        val gameView = initializeForView(MyGdxGame(this), config)
        layout.addView(gameView)
        return layout
    }

    override fun showToast(s: String) {
        runOnUiThread {
            Toast.makeText(
                context,
                s,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showComposeView(function: @Composable () -> Unit) {
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

    override fun showPlayers(
        playerList: MutableList<CompetitionOpponent>,
        status: Comp,
        runnable: OnCLickCallback?,
    ) = showComposeView{
        PlayerList(playerList,status) {
            binding.composeView.setGone()
            runnable?.call(it)
        }
    }

    override fun showNextSetMenu(
        player: Player,
        currentSet: Int,
        playerList: MutableList<CompetitionOpponent>,
        onFirstClick: OnCLickCallback?,
        onClose: OnCLickCallback?
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