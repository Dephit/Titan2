package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mygdx.game.Player
import com.mygdx.game.model.Notification

@Composable
fun HudBar(
    player: Player
) {
    val lastFiveMessages: List<Notification> = player.notificationManager.notificationList.filter { it.show }.sortedByDescending { it.showTime }.take(5)
    val exerciseProgress: Float = player.isInExercise?.let { bar -> bar.progress / bar.limit } ?: 0f

    Box(
        Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        MainParams(player)
        CurrentExercise(exerciseProgress = exerciseProgress, modifier = Modifier.fillMaxHeight())
        NotificationList(lastFiveMessages){
            player.notificationManager.countNotification()
        }
    }
}






