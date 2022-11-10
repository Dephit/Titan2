package com.sergeenko.alexey.titangym

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer


class AudioManager(
    val context: Context
    ) {

    private val mediaPlayer by lazy{
        MediaPlayer()
    }

    var isPlaying = false
        private set(value) {
            field = value
        }



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
        isPlaying = true
    }

    fun stopMusic(){
        mediaPlayer.stop()
        isPlaying = false
    }
}