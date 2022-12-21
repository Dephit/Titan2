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
import androidx.navigation.fragment.findNavController
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.mygdx.game.IActivityRequestHandler
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnClickBooleanCallback
import com.mygdx.game.interfaces.OnClickCallback
import com.sergeenko.alexey.titangym.*
import com.sergeenko.alexey.titangym.composeFunctions.*
import com.sergeenko.alexey.titangym.databinding.MainActivityBinding
import kotlinx.coroutines.*
import java.lang.Runnable

@SuppressLint("Registered")
class AndroidLauncherFragment : AndroidFragmentApplication(), ComposeFragmentInterface {

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

    override fun showProgressBar(
        title: String,
        onProgressEnd: OnClickCallback,
        onProgressUpdate: OnClickBooleanCallback,
        onClose: OnClickCallback
    ) {
        fun onClose(){
            hideComposeView()
            onClose.call(/* object = */ null)
        }

        showComposeView {
            val progress = remember { mutableStateOf(0f) }
            if(progress.value < 1f && onProgressUpdate.isConditionOk()){
                ProgressBar(
                    title = title,
                    onProgressEnd = onProgressEnd,
                    onClose = ::onClose,
                    state = progress.value,
                ){
                    progress.value += 0.1f
                }

            }else{
                onProgressEnd.call(null)
                onClose()
            }
        }
    }


}

interface ComposeFragmentInterface{

    fun requireContext(): Context

    fun requireActivity(): Activity

    fun runOnUiThread(unit: ()-> Unit)

    fun showComposeView(function: @Composable () -> Unit = {})

    fun hideComposeView()

    fun navController(): NavController

    fun postRunnable(runnable: Runnable)

    fun showHud(player: Player)

    fun showLoader()

    fun showProgressBar(
        title: String,
        onProgressEnd: OnClickCallback,
        onProgressUpdate: OnClickBooleanCallback,
        onClose: OnClickCallback
    )

}



