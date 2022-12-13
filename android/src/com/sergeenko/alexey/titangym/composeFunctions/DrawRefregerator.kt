package com.sergeenko.alexey.titangym.composeFunctions

import android.content.res.AssetManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun DrawInventory(
    container: Container,
    onItemClick: (Item) -> Unit,
    onClose: OnClickCallback
) {
    val assetManager = LocalContext.current.assets

    return Box(
        modifier = Modifier.clickable {
            onClose.call(null)
        }
    ) {
        Row(
            modifier = Modifier.padding(end = 30.dp)
        ) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Column {
                Spacer(fillMaxHeightModifier())
                Column(
                    modifier = round5Modifier
                        .clickable {

                        }
                ) {
                    CloseButton(onClose)
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(40.dp),
                        modifier = Modifier
                            .width(100.dp)
                            .height(200.dp)
                    ) {
                        container.items?.forEachIndexed { _, item ->
                            item {
                                Box(modifier = Modifier.padding(1.dp)){
                                    Column(Modifier.background(Color.Gray)) {
                                        assetManager?.getItemImage(item = item, onItemClick = onItemClick::invoke)
                                        Text(modifier = Modifier.align(CenterHorizontally), text = item.cost.toString())
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(
                    fillMaxHeightModifier()
                )
            }
        }
    }
}


