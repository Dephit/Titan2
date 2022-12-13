package com.sergeenko.alexey.titangym.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mygdx.game.model.items.Item
import com.mygdx.game.model.items.supplements.SupplementItem
import com.sergeenko.alexey.titangym.getItemImage

@Composable
fun PlayersActiveItem(sup: Item) {
    val am = LocalContext.current.assets
    Column(modifier = Modifier.background(Color.White)) {
        am.getItemImage(item = sup, onItemClick = {})
        if(sup is SupplementItem){
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(
                        RoundedCornerShape(50)
                    )
                ,
                color = Color.Black,
                progress = 1f -(sup.timeInUseLeft / sup.timeWillBeLast.toFloat()),
                backgroundColor = Color.Gray
            )
        }
    }
}