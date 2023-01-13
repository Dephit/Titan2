package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mygdx.game.Player
import com.sergeenko.alexey.titangym.featureGameScreen.models.ComposeState

@Composable
fun DrawPlayerStates(
    player: Player,
    state: ComposeState.OpenInventory,
    onClose: () -> Unit
) {
    val container = remember {
        player.inventoryManager.inventory.items.toMutableStateList()
    }

    return Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .padding(10.dp)
    ) {
        DrawStatsMenu(player)
        DrawInventory(
            container = container,
            itemsLimit = player.inventoryManager.supplements.totalCapacity,
            onItemClick = { state.onItemClicked.onClick(it) },
            onClose = onClose,
        )
    }

}


