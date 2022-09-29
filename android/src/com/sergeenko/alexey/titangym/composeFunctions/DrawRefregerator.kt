package com.sergeenko.alexey.titangym.composeFunctions

import android.content.res.AssetManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.model.Container
import com.mygdx.game.model.items.Item
import com.sergeenko.alexey.titangym.assetsToBitmap

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrawRefregerator(
    am: AssetManager?,
    container: Container,
    onItemClick: (Item) -> Unit,
    onClose: OnClickCallback
) {
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
                        modifier = Modifier.width(100.dp).height(200.dp)
                    ) {
                        container.items?.forEachIndexed { _, item ->
                            item {
                                am?.assetsToBitmap("screens/buttons/${item.styleName}.png")
                                    ?.asImageBitmap()?.let {
                                        Column {
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
                                            Text(text = item.cost.toString())
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