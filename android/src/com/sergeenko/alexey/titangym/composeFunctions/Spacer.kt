package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergeenko.alexey.titangym.composeFunctions.Spacer

@Composable
fun Spacer(width: Dp = 0.dp, height: Dp = 0.dp){
    Spacer(Modifier.height(height).width(width))
}

@Composable
fun FillSizeSpacer(width: Boolean = false, height: Boolean = false){
    val modifier = Modifier
    if(width){
        modifier.fillMaxWidth()
    }
    if(height){
        modifier.fillMaxHeight()
    }
    Spacer(modifier)
}

@Composable
fun RoundBackgroundText(text: String){
    Text(
        text = text,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .padding(3.dp),
        color = Color.Black
    )
}
