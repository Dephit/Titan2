package com.sergeenko.alexey.titangym.fragments

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sergeenko.alexey.titangym.AudioManager
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.composeFunctions.FillSpacer
import com.sergeenko.alexey.titangym.composeFunctions.titleTextSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class OptionsFragment : BaseComposeFragment() {

    private var isViewVisible = true
    val audioManager: AudioManager by inject()

    private val state = MutableStateFlow(true)
    private val progress: StateFlow<Boolean> = state.asStateFlow()
    private val musicState = MutableStateFlow(audioManager.isPlaying)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawView()
    }

    private fun drawView(){
        setContent {
            val text = musicState.collectAsState()

            Box(Modifier) {
                Column {
                    FillSpacer()
                    Button(onClick = {
                        onBackPress()
                    }) {
                        Text(
                            text = stringResource(id = R.string.back),
                            color = Color.White,
                            fontSize = titleTextSize,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Button(onClick = {
                        changeMusicSettings()
                    }) {
                        Text(
                            text = if(text.value) stringResource(id = R.string.turn_off_music) else stringResource(id = R.string.turn_on_music),
                            color = Color.White,
                            fontSize = titleTextSize,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Button(onClick = {
                        navigate(R.id.action_optionsFragment_to_menu)
                    }) {
                        Text(
                            text = stringResource(id = R.string.to_menu),
                            color = Color.White,
                            fontSize = titleTextSize,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    FillSpacer()
                }
            }
        }
    }

    private fun changeMusicSettings() {
        if(audioManager.isPlaying){
            audioManager.stopMusic()
            musicState.value = false
        }else{
            audioManager.playMusic()
            musicState.value = true
        }
    }

    private suspend fun blindingText() {
        if(isViewVisible) {
            state.value = !state.value
            withContext(Dispatchers.Main) {
                drawView()
            }
            delay(1000)
            blindingText()
        }
    }


}