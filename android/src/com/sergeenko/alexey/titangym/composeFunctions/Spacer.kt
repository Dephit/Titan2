package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*

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
