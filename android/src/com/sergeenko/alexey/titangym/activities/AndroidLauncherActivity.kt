package com.sergeenko.alexey.titangym.activities

import androidx.appcompat.app.AppCompatActivity
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import android.os.Bundle
import android.view.Window
import com.kitegroup.adlibrary.AppOpenManager
import com.sergeenko.alexey.titangym.AudioManager
import com.sergeenko.alexey.titangym.R
import org.koin.android.ext.android.inject

class AndroidLauncherActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    val appOpenManager: AppOpenManager by inject()
    val audioManager: AudioManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity)
        audioManager.playMusic()
    }

    override fun onStart() {
        super.onStart()
        appOpenManager.showAdIfAvailable {

        }
    }

    override fun exit() {
        System.exit(0)
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {
        super.onPointerCaptureChanged(hasCapture)
    }
}