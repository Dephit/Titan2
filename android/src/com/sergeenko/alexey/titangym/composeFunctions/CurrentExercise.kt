package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.sergeenko.alexey.titangym.*

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
