package com.sergeenko.alexey.titangym.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.os.bundleOf
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.activities.AndroidLauncherActivity
import com.sergeenko.alexey.titangym.composeFunctions.FillSpacer
import com.sergeenko.alexey.titangym.composeFunctions.titleTextSize
import com.sergeenko.alexey.titangym.core.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.ext.android.inject

class MainMenuFragment : BaseComposeFragment() {

    companion object{

        const val LOAD_PLAYER = "LOAD_PLAYER"
    }

    private val preferenceManager: PreferenceManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawView()
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPress()
        }
    }

    private fun drawView(){
        val player = preferenceManager.player
        val hasPlayer = !player.isNullOrEmpty()
        setContent {
            Box {
                Column {
                    FillSpacer()
                    if(hasPlayer) {
                        Button(onClick = {
                            navigate(R.id.action_mainMenuFragment_to_androidLauncherFragment, bundleOf(LOAD_PLAYER to true))
                        }) {
                            Text(
                                text = stringResource(id = R.string.launch_game),
                                color = Color.White,
                                fontSize = titleTextSize,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    Button(onClick = {
                        navigate(R.id.action_mainMenuFragment_to_androidLauncherFragment)
                    }){
                        Text(
                            text = stringResource(id = R.string.new_game),
                            color = Color.White,
                            fontSize = titleTextSize,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(onClick = {
                        navigate(R.id.action_mainMenuFragment_to_optionsFragment)
                    }){
                        Text(
                            text = stringResource(id = R.string.options),
                            color = Color.White,
                            fontSize = titleTextSize,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(onClick = {
                        onBackPress()
                    }){
                        Text(
                            text = stringResource(id = R.string.exit),
                            color = Color.White,
                            fontSize = titleTextSize,
                            textAlign = TextAlign.Center,
                        )
                    }
                    FillSpacer()
                }
            }
        }
    }

    override fun onBackPress() {
        (activity as AndroidLauncherActivity).exit()
    }


}

