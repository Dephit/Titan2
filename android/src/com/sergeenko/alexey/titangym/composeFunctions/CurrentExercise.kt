package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mygdx.game.Exercise
import com.sergeenko.alexey.titangym.fillMaxHeightModifier
import com.sergeenko.alexey.titangym.linearProgressModifier
import com.sergeenko.alexey.titangym.round5Modifier

@Composable
fun CurrentExercise(bar: Exercise?) {
    val exerciseProgress: Float = bar?.let { bar.progress / bar.limit } ?: 0f

    Column {
        Spacer(fillMaxHeightModifier())
        LinearProgressIndicator(
            modifier = round5Modifier
                .linearProgressModifier()
                .width(150.dp),
            color = Color.White,
            progress = exerciseProgress,
            backgroundColor = Color.Black
        )
    }
}