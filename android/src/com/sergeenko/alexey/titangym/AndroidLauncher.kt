package com.sergeenko.alexey.titangym

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnCLickCallback
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.enums.Comp
import com.sergeenko.alexey.titangym.composeFunctions.AlertDialogSample
import com.sergeenko.alexey.titangym.composeFunctions.CompetitionTable
import com.sergeenko.alexey.titangym.composeFunctions.PlayerList
import com.sergeenko.alexey.titangym.databinding.MainActivityBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    override fun showDialog(
        title: String,
        subtitle: String,
        agreeText: String,
        closeText: String,
        onClose: OnCLickCallback,
        onAgree: OnCLickCallback
    ){
        showComposeView {
                AlertDialogSample(
                    title = title,
                    subtitle = subtitle,
                    agreeText = agreeText,
                    closeText = closeText,
                    onClose = {
                        binding.composeView.setGone()
                        onClose.call(it)
                    },
                    onAgree = {
                        binding.composeView.setGone()
                        onAgree.call(it)
                    }
                )
        }
    }

    override fun showDialog(
        title: String,
        subtitle: String,
        onClose: OnCLickCallback,
        onAgree: OnCLickCallback
    ){
        showComposeView {
            AlertDialogSample(
                title = title,
                subtitle = subtitle,
                onClose = {
                    binding.composeView.setGone()
                    onClose.call(it)
                },
                onAgree = {
                    binding.composeView.setGone()
                    onAgree.call(it)
                }
            )
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

class MainViewModel : ViewModel() {
    // Initial value is false so the dialog is hidden
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        _showDialog.value = false
        // Continue with executing the confirmed action
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    // The rest of your screen's logic...
}