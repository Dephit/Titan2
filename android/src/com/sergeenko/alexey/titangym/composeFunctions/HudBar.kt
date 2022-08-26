package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mygdx.game.Player

@Composable
@Preview
fun HudBar(
    player: Player
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        Row {
            Spacer(Modifier.fillMaxWidth().weight(1f))
            LinearProgressIndicator(
                modifier = Modifier
                    .width(100.dp)
                    .height(10.dp)
                    .clip(RoundedCornerShape(50)
                    )
                ,
                color = Color.Yellow,
                progress = player.energyBar.currentAmount / 100f,
                backgroundColor = Color.Black
            )
        }
    }
}