package com.sergeenko.alexey.titangym.composeFunctions

import android.content.res.AssetManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.model.Container
import com.mygdx.game.model.items.Item
import com.sergeenko.alexey.titangym.*
import com.sergeenko.alexey.titangym.items.InventoryItem
import com.sergeenko.alexey.titangym.items.PlayersActiveItem

@Composable
fun DrawInventory(
    widthModifier: Modifier = blockModifier.width(120.dp),
    container: Container,
    gridCound: Int = 2,
    onItemClick: (Item) -> Unit,
    onClose: () -> Unit
) = Row(
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier

) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = widthModifier
            .fillMaxHeight()
    ) {
        CloseButton{
            onClose()
        }
        LazyVerticalGrid(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(count = gridCound),
            modifier = Modifier
        ) {
            items(items = container.items){ item ->
                InventoryItem(item = item, onItemClick = onItemClick)
            }
        }
    }
}



