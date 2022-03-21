package com.sergeenko.alexey.titangym.fragments

import android.os.Bundle
import android.view.View
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
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.composeFunctions.FillSpacer
import com.sergeenko.alexey.titangym.composeFunctions.titleTextSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class OptionsFragment : BaseComposeFragment() {

    private var isViewVisible = true

    private val state = MutableStateFlow(true)
    private val progress: StateFlow<Boolean> = state.asStateFlow()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawView()
    }

    private fun drawView(){
        setContent {
            Box(Modifier.clickable {
                isViewVisible = false
                navigate(R.id.action_splashFragment_to_androidLauncherFragment)

            }) {
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

                    FillSpacer()
                }
            }
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