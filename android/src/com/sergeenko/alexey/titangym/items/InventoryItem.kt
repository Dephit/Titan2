package com.sergeenko.alexey.titangym.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.mygdx.game.model.items.Item
import com.sergeenko.alexey.titangym.*
import com.sergeenko.alexey.titangym.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun InventoryItem(item: Item, onItemClick: (Item) -> Unit) {
    val assetManager = LocalContext.current.assets
    val bitmap = remember(item) {
        derivedStateOf { assetManager.getBitmap(item) }
    }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .clickable { onItemClick(item) }
            .padding(5.dp)
            .border(1.dp, LightBlue)
            .background(DarkBlue)
    ) {
        bitmap.value?.let {
            Image(
                bitmap = it,
                contentDescription = "Localized description",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .offset(y = 3.dp)
                    .border(1.dp, LightBlue)
                    .background(DarkBlue)
                    .width(40.dp)
                    .height(40.dp)
            )
        }
        Text(text = stringResource(id = R.string.item_cost, item.cost))
    }
}

@Composable
fun NoInventoryItem(item: Item) {
    val assetManager = LocalContext.current.assets
    val bitmap = remember(item) {
        derivedStateOf { assetManager.getBitmap(item) }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .clickable { }
            .padding(5.dp)
            .border(1.dp, LightBlue)
            .background(DarkBlue)
    ) {
        bitmap.value?.let {
            Image(
                bitmap = it,
                contentDescription = "Localized description",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .offset(y = 3.dp)
                    .border(1.dp, LightBlue)
                    .background(DarkBlue)
                    .width(40.dp)
                    .height(40.dp)
            )
        }
        Text(text = "")
    }
}