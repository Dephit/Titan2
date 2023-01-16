package com.sergeenko.alexey.titangym.optionsFeature.viewModels;

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sergeenko.alexey.titangym.core.AudioManager

class OptionsViewModel(
        private val audioManager: AudioManager
) : ViewModel() {

    private val _musicState = mutableStateOf(audioManager.isMusicOn)
    val musicState: State<Boolean> = _musicState

    fun changeMusicSettings() {
        audioManager.changeMusic()
    }

}