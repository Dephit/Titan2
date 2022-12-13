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
import com.mygdx.game.model.Notification
import com.sergeenko.alexey.titangym.fillMaxWidthModifier
import com.sergeenko.alexey.titangym.items.MessageItem

@Composable
fun NotificationList(notificationManager: NotificationManager) {
    Row(
        modifier = Modifier.padding(end = 10.dp)
    ) {
        val lastFiveMessages = notificationManager.notificationList.filter { it.show }.sortedByDescending { it.showTime }.take(5)

        Spacer(modifier = Modifier
            .fillMaxWidth()
            )
        Column{
            lastFiveMessages.forEach {
                MessageItem(it)
            }
        }
    }
    notificationManager.countNotification()
}

