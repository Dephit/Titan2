package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mygdx.game.Exercise
import com.mygdx.game.Player
import com.mygdx.game.managers.NotificationManager

@Composable
fun HudBar(
    player: Player
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        MainParams(player)
        CurrentExercise(player.isInExercise)
        NotificationList(player.notificationManager)
    }
}






