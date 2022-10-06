package com.sergeenko.alexey.titangym

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.mygdx.game.model.items.Item
import java.io.File
import java.io.IOException

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun AssetManager.assetsToBitmap(fileName: String): Bitmap?{
    return try {
        with(open(fileName)){
            BitmapFactory.decodeStream(this)
        }
    } catch (e: IOException) { null }
}

fun Fragment.getAssetManager() = requireContext().assets

fun Item.getItemPath() = if(!styleName.contains(".png")) "screens/buttons/${styleName}.png" else styleName

fun AssetManager.getBitmap(item: Item) = assetsToBitmap(item.getItemPath())?.asImageBitmap()

@Composable
fun AssetManager.getItemImage(item: Item, onItemClick: (Item) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .width(40.dp)
            .height(40.dp)
            .clickable {
                onItemClick(item)
            }
        getBitmap(item)?.let {
            Image(bitmap = it,
                contentDescription = "Localized description",
                contentScale = ContentScale.FillBounds,
                modifier = modifier
            )
        }?: Text(modifier = modifier.background(Color.White), text = item.title, color = Color.Black)
    }
}
