package com.sergeenko.alexey.titangym

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Stable
val LightBlue: Color
    get() = Color(0xFFDAFFFF)
@Stable
val DarkBlue: Color
    get() = Color(0xFF9AE4FF)

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
    .clip(RoundedCornerShape(5.dp))
    .background(LightBlue)
    .border(1.dp, DarkBlue, shape = RoundedCornerShape(5.dp))
    .padding(5.dp)

val blockModifier = Modifier
    .clip(RoundedCornerShape(25.dp))
    .background(LightBlue)
    .border(1.dp, DarkBlue, shape = RoundedCornerShape(25.dp))
    .padding(10.dp)

