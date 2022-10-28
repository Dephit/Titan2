package com.sergeenko.alexey.titangym.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.os.bundleOf
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.activities.AndroidLauncher
import com.sergeenko.alexey.titangym.composeFunctions.FillSpacer
import com.sergeenko.alexey.titangym.composeFunctions.titleTextSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainMenuFragment : BaseComposeFragment() {

    private var isViewVisible = true

    private val state = MutableStateFlow(true)
    private val progress: StateFlow<Boolean> = state.asStateFlow()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawView()
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPress()
        }
    }

    private fun drawView(){
        val preferences = context?.getSharedPreferences(context!!.packageName, MODE_PRIVATE)
        val player = preferences?.getString("PLAYER", null)
        setContent {
            Box {
                Column {
                    FillSpacer()
                    if(player != null) {
                        Button(onClick = {
                            navigate(R.id.action_mainMenuFragment_to_androidLauncherFragment, bundleOf("PLAYER" to player))
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
        (activity as AndroidLauncher).exit()
    }


}

