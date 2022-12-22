package com.sergeenko.alexey.titangym.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mygdx.game.model.items.Item
import com.sergeenko.alexey.titangym.getItemImage
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun InventoryItem(item: Item, onItemClick: (Item) -> Unit) {
    val assetManager = LocalContext.current.assets

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier.padding(3.dp).border(2.dp, Color.DarkGray).background(Color.LightGray)
    ) {
        assetManager?.getItemImage(item = item, onItemClick = onItemClick::invoke)
        Text(modifier = Modifier.align(alignment = CenterHorizontally), text = item.cost.toString())
    }
}