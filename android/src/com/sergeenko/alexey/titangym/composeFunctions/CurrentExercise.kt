package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mygdx.game.Exercise
import com.sergeenko.alexey.titangym.fillMaxHeightModifier
import com.sergeenko.alexey.titangym.linearProgressModifier
import com.sergeenko.alexey.titangym.round5Modifier

@Composable
fun CurrentExercise(
    exerciseProgress: Float?,
    modifier: Modifier = Modifier,
    progressColor: Color = Color.White,
    backgroundColor: Color = Color.Black,
    modifierColor: Modifier = round5Modifier
) {
    val condition = exerciseProgress == null

    val color = animateColorAsState(if (condition) Color.Transparent else Color.Gray)
    val darkColor = animateColorAsState(if (condition) Color.Transparent else Color.DarkGray)
    val progresscolor = animateColorAsState(if (condition) Color.Transparent else progressColor)

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
    ){

        Box(
            Modifier.padding(1.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .width(160.dp)
                    .border(5.dp, darkColor.value, shape = RoundedCornerShape(25.dp))
                    .height(10.dp)
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .width(160.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .height(10.dp)
                    .padding(2.dp),
                color = progresscolor.value,
                progress = exerciseProgress ?: 0f,
                backgroundColor = color.value
            )
        }
    }
}

@Composable
fun StatExercise(
    exerciseProgress: Float,
    progressColor: Color = Color.White,
    backgroundColor: Color = Color.Black,
    modifierColor: Modifier = round5Modifier
) {
    Spacer(10.dp)
    Box(){
        Box(modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .width(250.dp)
            .border(5.dp, Color.DarkGray, shape = RoundedCornerShape(25.dp))
            .height(10.dp))
        LinearProgressIndicator(
            modifier = modifierColor
                .width(250.dp)
                .clip(RoundedCornerShape(25.dp))
                .height(10.dp)
                .padding(2.dp),
            color = progressColor,
            progress = exerciseProgress,
            backgroundColor = Color.Gray
        )
    }

}