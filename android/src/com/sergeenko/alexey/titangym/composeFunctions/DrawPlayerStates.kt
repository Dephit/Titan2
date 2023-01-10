package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mygdx.game.Player
import com.mygdx.game.model.Container
import com.mygdx.game.model.items.Item

@Composable
fun DrawPlayerStates(
    player: Player,
    container: List<Item>,
    onItemClicked: (Item) -> Unit,
    onClose: () -> Unit,
) {

    return Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .padding(10.dp)
    ) {
        DrawStatsMenu(player)
        DrawInventory(
            container = container,
            onItemClick = onItemClicked,
            onClose = onClose,
            itemsLimit = player.inventoryManager.supplements.totalCapacity
        )
    }

}


