package com.sergeenko.alexey.titangym.items

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
import com.sergeenko.alexey.titangym.round5Modifier

@Composable
fun MessageItem(it: Notification) {
    Text(
        text = it.message,
        modifier = round5Modifier,
        color = Color.Black
    )
    Spacer(
        Modifier.height(3.dp)
    )
}