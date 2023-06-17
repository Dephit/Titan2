package com.sergeenko.alexey.titangym.core

import android.content.Context
import android.media.MediaPlayer

class AudioManager(
    private val context: Context,
    private val preferenceManager: PreferenceManager
) {

    private val mediaPlayer by lazy{
        MediaPlayer()
    }

    val isMusicOn
        get() = preferenceManager.isMusicOn

    fun playMusic(){
        val music = context.assets.openFd("music/music.mp3")
        try {
            mediaPlayer.setDataSource(music.fileDescriptor, music.startOffset, music.length)
        }catch (e: Exception){

        }

        music.close()

        mediaPlayer.setVolume(100f, 100f)
        mediaPlayer.prepare()
        mediaPlayer.start()
        preferenceManager.isMusicOn = true
    }

    private fun stopMusic(){
        mediaPlayer.stop()
        preferenceManager.isMusicOn = false
    }

    fun changeMusic() {
        if(isMusicOn){
            stopMusic()
        }else{
            playMusic()
        }
    }
}
