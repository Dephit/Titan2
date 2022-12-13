package com.sergeenko.alexey.titangym.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
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
import com.sergeenko.alexey.titangym.composeFunctions.*
import com.sergeenko.alexey.titangym.databinding.MainActivityBinding
import kotlinx.coroutines.*
import java.lang.Runnable

@SuppressLint("Registered")
class AndroidLauncherFragment : AndroidFragmentApplication(), ComposeFragmentInterface {

    override val scope: CoroutineScope
        get() = scope

    override fun runOnUiThread(unit: () -> Unit) {
        super.runOnUiThread(unit)
    }

    private val viewModel: MainViewModel by viewModels()
    private val gameLayout: RelativeLayout by lazy {
        createGameLayout()
    }
    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }
    private val gameRequestHandler: IActivityRequestHandler by lazy {
        GameRequestHandler(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
        launchGame()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    private fun createGameLayout(): RelativeLayout {
        val layout = RelativeLayout(context)
        val config: AndroidApplicationConfiguration = AndroidApplicationConfiguration()
        val myGdxGame = MyGdxGame(/* handler = */ gameRequestHandler, /* player = */arguments?.getString("PLAYER"))
        setFullScreenFlags()
        val gameView = initializeForView(myGdxGame, config)
        layout.addView(gameView)
        return layout
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


    override fun showComposeView(function: @Composable () -> Unit) {
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

    override fun hideComposeView() {
        binding.composeView.setGone()
    }

    override fun navController() = findNavController()

    override fun showHud(player: Player) {
        binding.hudView.apply {
            setContent {
                HudBar(player)
            }
        }
    }

    override fun showLoader() {
        showComposeView {
            CircularProgressIndicatorSample()
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
                            hideComposeView()
                            onClose.call(it)
                        },
                        onAgree = {
                            viewModel.onDialogConfirm()
                            hideComposeView()
                            onAgree.call(it)
                        },
                        state = viewModel.showDialog
                    )
                }
            }
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
                withContext(Dispatchers.Main){
                    showComposeView {
                        ProgressBar(
                            title = title,
                            onProgressEnd = onProgressEnd,
                            onClose = {
                                work = false
                                hideComposeView()
                                onClose.call(it)
                            },
                            state = viewModel.progress)
                    }
                }
                delay(1000)
            }
            hideComposeView()
            onProgressEnd.call(null)
            onClose.call(null)
        }
    }


}

interface ComposeFragmentInterface{

    val scope: CoroutineScope

    fun requireContext(): Context

    fun requireActivity(): Activity

    fun runOnUiThread(unit: ()-> Unit)

    fun showComposeView(function: @Composable () -> Unit = {})

    fun hideComposeView()

    fun navController(): NavController

    fun postRunnable(runnable: Runnable)

    fun showHud(player: Player)

    fun showLoader()

    fun showDialog(
        title: String,
        subtitle: String,
        agreeText: String,
        closeText: String,
        onClose: OnClickCallback,
        onAgree: OnClickCallback
    )

    fun showProgressBar(
        title: String,
        onProgressEnd: OnClickCallback,
        onProgressUpdate: OnClickBooleanCallback,
        onClose: OnClickCallback
    )

}



