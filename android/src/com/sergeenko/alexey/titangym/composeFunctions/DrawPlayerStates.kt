package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mygdx.game.Exercise
import com.mygdx.game.Player
import com.mygdx.game.model.items.Item
import com.sergeenko.alexey.titangym.items.ExerciseStatItem
import com.sergeenko.alexey.titangym.linearProgressModifier
import com.sergeenko.alexey.titangym.round5Modifier

@Composable
fun DrawPlayerStates(
    player: Player,
    onItemClicked: (Item) -> Unit,
    onClose: () -> Unit,
) {
    return Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 30.dp)
    ) {
        DrawStatsMenu(player)
        DrawInventory(
            container = player.inventoryManager.inventory,
            onItemClick = onItemClicked,
            onClose = onClose
        )
    }

}


