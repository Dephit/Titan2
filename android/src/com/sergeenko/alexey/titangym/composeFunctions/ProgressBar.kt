package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnCLickCallback
import com.mygdx.game.managers.InventoryManager
import com.mygdx.game.model.Inventory
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.items.PlayerComposeItem
import com.sergeenko.alexey.titangym.items.TableHeaderComposeItem
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProgressBar(
    title: String,
    closeText: String = stringResource(id = R.string.close),
    onProgressEnd: OnCLickCallback,
    onClose: OnCLickCallback,
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

