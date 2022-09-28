package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mygdx.game.interfaces.OnClickCallback
import com.sergeenko.alexey.titangym.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProgressBar(
    title: String,
    closeText: String = stringResource(id = R.string.close),
    onProgressEnd: OnClickCallback,
    onClose: OnClickCallback,
    state: StateFlow<Float>,
) {
    DialogBox {
        Column {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = progressBarModifier()
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title, fontSize = titleTextSize)
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(30.dp)
                        .clip(RoundedCornerShape(5))
                        .fillMaxWidth(),
                    progress = state.value
                )
                Row{
                    CloseButton(onClose = onClose)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

