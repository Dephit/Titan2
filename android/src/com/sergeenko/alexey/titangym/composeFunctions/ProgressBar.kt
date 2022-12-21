package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mygdx.game.interfaces.OnClickCallback
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.clipRound
import com.sergeenko.alexey.titangym.linearProgressModifier
import com.sergeenko.alexey.titangym.round5Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun ProgressBar(
    title: String,
    closeText: String = stringResource(id = R.string.close),
    onProgressEnd: OnClickCallback,
    onClose: ()->Unit,
    state: Float,
    onProgressUpdate: ()->Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.clickable {
            onClose()
        }
    ) {
        Column(
            modifier = round5Modifier
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontSize = titleTextSize)
            LinearProgressIndicator(
                modifier = Modifier
                    .height(20.dp)
                    .clipRound()
                    .fillMaxWidth(),
                progress = state
            )
            Row{
                CloseButton(onClose = {
                    onClose()
                })
            }
        }
        rememberCoroutineScope().launch {
            delay(1000)
            onProgressUpdate()
        }
    }
}

