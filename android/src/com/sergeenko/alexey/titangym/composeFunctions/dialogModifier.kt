package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val titleTextSize = 30.sp

@Composable
fun dialogModifier(): Modifier {
    return Modifier.fillMaxSize()
        .clip(RoundedCornerShape(5))
        .background(Color.White)
        .padding(10.dp)
}

@Composable
fun progressBarModifier(): Modifier {
    return Modifier
        .clip(RoundedCornerShape(5))
        .background(Color.White)
        .padding(10.dp)
}

@Composable
fun ColumnScope.FillSpacer() = Spacer(modifier = Modifier.weight(1f))