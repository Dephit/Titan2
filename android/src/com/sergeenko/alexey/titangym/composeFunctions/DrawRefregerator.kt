package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import com.mygdx.game.model.Container
import com.mygdx.game.model.items.Item
import com.mygdx.game.model.items.perks.NoItem
import com.sergeenko.alexey.titangym.blockModifier
import com.sergeenko.alexey.titangym.items.InventoryItem
import com.sergeenko.alexey.titangym.items.NoInventoryItem

@Composable
fun DrawInventory(
    widthModifier: Modifier = blockModifier.width(120.dp),
    showCloseBotton: Boolean = true,
    container: Container,
    itemsLimit: Int? = null,
    gridCound: Int = 2,
    onItemClick: (Item) -> Unit,
    onClose: () -> Unit
) = DrawInventory(
    container = container.items,
    showCloseBotton = showCloseBotton,
    widthModifier =widthModifier,
    itemsLimit = itemsLimit,
    gridCound = gridCound,
    onItemClick = onItemClick,
    onClose = onClose
)



@Composable
fun DrawInventory(
    widthModifier: Modifier = blockModifier.width(180.dp),
    showCloseBotton: Boolean = true,
    itemsLimit: Int? = null,
    container: List<Item>,
    gridCound: Int = 2,
    onItemClick: (Item) -> Unit,
    onClose: () -> Unit
) = Row(
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.CenterVertically,
    modifier = widthModifier.fillMaxHeight()
) {

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxHeight()
    ) {
        if(showCloseBotton){
            CloseButton{
                onClose()
            }
        }
        LazyVerticalGrid(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(count = gridCound),
        ) {
            items(items = container){ item ->
                InventoryItem(item = item, onItemClick = onItemClick)
            }
            itemsLimit?.let {
                for (i in 0 until it - container.size){
                    item {
                        NoInventoryItem(item = NoItem())
                    }
                }
            }
        }
    }
}