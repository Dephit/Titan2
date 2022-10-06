package com.sergeenko.alexey.titangym.composeFunctions

import android.content.res.AssetManager
import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.model.items.Item
import com.mygdx.game.model.items.perks.PerkItem
import com.mygdx.game.model.items.perks.PerksMenu
import com.sergeenko.alexey.titangym.assetsToBitmap
import com.sergeenko.alexey.titangym.getBitmap
import com.sergeenko.alexey.titangym.getItemImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrawPerkMenu(
    assetManager: AssetManager,
    player: Player?,
    onItemClick: (Item) -> Unit,
    onClose: OnClickCallback
) {
    return Box(
        modifier = dialogModifier().clickable {
            onClose.call(null)
        }
    ) {
        Row(
            modifier = Modifier.padding(end = 30.dp)
        ) {
            CloseButton(onClose)
            Column() {
                Spacer(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
                LazyColumn(
                    modifier = Modifier
                        .width(100.dp)
                        .height(200.dp)
                ) {
                    PerksMenu().items.map { it as PerkItem }.forEachIndexed { _, item ->
                        item {
                            LazyRow{
                                item{
                                    PerkItem(
                                        assetManager,
                                        item,
                                        onItemClick
                                    )

                                }
                                item.childPerk.forEach {
                                    item{
                                        PerkItem(assetManager, it, onItemClick)
                                    }
                                }
                            }

                        }
                    }
                }
                Spacer(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PerkItem(assetManager: AssetManager, perk: PerkItem, onItemClick: (Item) -> Unit){
    Box(modifier = Modifier.padding(1.dp)){
        Column(Modifier.background(Color.Gray)) {
            assetManager.getItemImage(perk, onItemClick)
            Text(text = perk.cost.toString())
        }
    }
}