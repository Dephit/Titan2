package com.sergeenko.alexey.titangym.composeFunctions

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
    return DialogBox {
        Column(
            modifier = dialogModifier()
        ) {
            CloseButton(onClose)
            LazyVerticalGrid(
                cells = GridCells.Adaptive(40.dp),
                modifier = Modifier.width(100.dp)
            ) {
                im?.inventory?.items?.forEachIndexed { _, item ->
                    item {
                        am?.assetsToBitmap("screens/buttons/${item.styleName}.png")?.asImageBitmap()?.let {
                            Image(bitmap = it,
                                contentDescription = "Localized description",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                                    .clickable {
                                        onItemClick(item)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun AssetManager.assetsToBitmap(fileName: String): Bitmap?{
    return try {
        with(open(fileName)){
            BitmapFactory.decodeStream(this)
        }
    } catch (e: IOException) { null }
}