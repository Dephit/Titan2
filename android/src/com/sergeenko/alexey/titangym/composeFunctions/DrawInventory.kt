package com.sergeenko.alexey.titangym.composeFunctions

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mygdx.game.interfaces.OnCLickCallback
import com.mygdx.game.managers.InventoryManager
import com.mygdx.game.model.Container
import com.mygdx.game.model.Inventory
import com.mygdx.game.model.Item
import com.sergeenko.alexey.titangym.items.TableHeaderComposeItem
import java.io.IOException
import java.util.ArrayList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrawInventory(
    am: AssetManager?,
    im: InventoryManager?,
    onItemClick: (Item) -> Unit,
    onClose: OnCLickCallback
) {
    var state: Item? by remember {
        mutableStateOf(null)
    }

    return if(state == null){
        Box(
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
                Column() {
                    Spacer(
                        Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5))
                            .background(Color.White)
                            .padding(10.dp)
                    ) {
                        CloseButton(onClose)
                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(40.dp),
                            modifier = Modifier.width(100.dp)
                        ) {
                            im?.inventory?.items?.forEachIndexed { _, item ->
                                item {
                                    am?.assetsToBitmap("screens/buttons/${item.styleName}.png")
                                        ?.asImageBitmap()?.let {
                                            Image(bitmap = it,
                                                contentDescription = "Localized description",
                                                contentScale = ContentScale.FillBounds,
                                                modifier = Modifier
                                                    .width(40.dp)
                                                    .height(40.dp)
                                                    .clickable {
                                                        //onItemClick(item)
                                                        state = item
                                                    }
                                            )
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
    }else {
        Box(modifier = Modifier.clickable {
            onClose.call(null)
        }) {
            Box(
                modifier = Modifier
                    .size(300.dp, 150.dp)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(5))
                    .background(Color.White)
                    .padding(10.dp)

            ) {
                Row(
                    modifier = Modifier.padding(end = 30.dp)
                ) {
                    Text(text = "Buy", modifier = Modifier.clickable {
                        onItemClick(state!!)
                        state = null
                    })
                    Text(text = "Cancel", modifier = Modifier.clickable {
                        state = null
                        onClose.call(null)
                    })
                }
            }
        }
    }
}



fun AssetManager.assetsToBitmap(fileName: String): Bitmap?{
    return try {
        with(open(fileName)){
            BitmapFactory.decodeStream(this)
        }
    } catch (e: IOException) { null }
}