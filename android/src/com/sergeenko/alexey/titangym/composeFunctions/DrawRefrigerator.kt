package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mygdx.game.Player
import com.mygdx.game.model.Container
import com.mygdx.game.model.items.Item
import com.mygdx.game.model.items.OnItemClick

@Composable
fun DrawRefrigerator(player: Player, onItemUse: OnItemClick, function: () -> Unit) {
        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
        ) {
            DrawStatsMenu(player = player, widthFraction = 0.7f)
            DrawInventory(
                container = player.inventoryManager.refrigerator,
                itemsLimit = player.inventoryManager.refrigerator.totalCapacity,
                onItemClick = {
                    onItemUse.onClick(it)
                },
                gridCound = 3,
                widthModifier = blockModifier.weight(0.4f)
            ) {
                function()
            }
        }
    }