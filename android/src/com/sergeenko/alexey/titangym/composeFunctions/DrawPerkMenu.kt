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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
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
            //onClose.call(null)
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

                ) {
                    PerksMenu().items.map { it as PerkItem }.forEachIndexed { _, item ->
                        val bgColor = if(player?.inventoryManager?.hasPerk(item) == true) Color.Yellow else Color.Gray
                        item {
                            LazyRow{
                                item{
                                    PerkItem(
                                        assetManager,
                                        item,
                                        bgColor,
                                        onItemClick
                                    )

                                }
                                item.childPerk.forEach {
                                    val bgColor = if(player?.inventoryManager?.hasPerk(it) == true) Color.Yellow else Color.Gray
                                    item{
                                        Row(
                                            modifier = Modifier.height(40.dp)
                                        ) {
                                            Box(modifier = Modifier
                                                .size(width = 30.dp, height = 2.dp)
                                                .align(CenterVertically)
                                                .background(color = bgColor))
                                        }
                                    }
                                    item{
                                        PerkItem(assetManager, it, bgColor, onItemClick)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(30.dp))
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
private fun PerkItem(assetManager: AssetManager, perk: PerkItem, bgColor: Color, onItemClick: (Item) -> Unit){
    Box(modifier = Modifier){
        Column(Modifier
            .background(bgColor)
            .padding(1.dp)) {
            assetManager.getItemImage(perk, onItemClick)
            //Text(text = perk.cost.toString(), textAlign = TextAlign.Center, modifier = Modifier.align(CenterHorizontally), color = Color.White)
        }
    }
}