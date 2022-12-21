package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mygdx.game.model.Notification
import com.sergeenko.alexey.titangym.items.MessageItem

@Composable
fun NotificationList(lastFiveMessages: List<Notification>, onCount: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
            content = {
            items(lastFiveMessages){
                MessageItem(it)
            }
        })
    }
    onCount()
}

