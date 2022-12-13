package com.sergeenko.alexey.titangym

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun RowScope.fillMaxWidthModifier() = Modifier
    .fillMaxWidth()
    .weight(1f)

fun ColumnScope.fillMaxWidthModifier() = Modifier
    .fillMaxWidth()
    .weight(1f)


fun RowScope.fillMaxHeightModifier() = Modifier
    .fillMaxHeight()
    .weight(1f)

fun ColumnScope.fillMaxHeightModifier() = Modifier
    .fillMaxHeight()
    .weight(1f)

fun Modifier.linearProgressModifier() = height(5.dp).clipRound()

fun Modifier.clipRound(percent: Int = 50) = clip(RoundedCornerShape(percent))

val round5Modifier = Modifier
    .clipRound(5)
    .background(Color.White)
    .padding(5.dp)
