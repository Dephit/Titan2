package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.unit.*
import com.mygdx.game.*
import com.sergeenko.alexey.titangym.fragments.*

@Composable
fun DrawPlayerInvertory(
    player: Player,
    state: ComposeState.OpenInventory,
    onClose: () -> Unit
) {
    return Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .padding(10.dp)
    ) {
        DrawStatsMenu(player)
        DrawInventory(
            container = player.inventoryManager.inventory.items,
            itemsLimit = player.inventoryManager.supplements.totalCapacity,
            onItemClick = { state.onItemClicked.onClick(it) },
            onClose = onClose,
        )
    }

}


