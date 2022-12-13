package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mygdx.game.Exercise
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnClickCallback
import com.sergeenko.alexey.titangym.round5Modifier

@Composable
fun DrawPlayerStates(
    player: Player,
    onClose: OnClickCallback
) {
    return Box(
        modifier = Modifier.clickable {
            onClose.call(null)
        }
    ) {
        Row(
            modifier = Modifier.padding(end = 190.dp)
        ) {
            Spacer(width = 30.dp)
            Column {
                Spacer(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
                Column(
                    modifier = round5Modifier
                        .clickable {

                        }
                        .height(290.dp)
                        .fillMaxWidth()
                ) {
                    LazyColumn{
                        item {
                            ExerciseStat(player.exerciseManager.squatExr)
                        }
                        item {
                            ExerciseStat(player.exerciseManager.bench)
                        }
                        item {
                            ExerciseStat(player.exerciseManager.deadlift)
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

@Composable
fun ExerciseStat(exercise: Exercise?) {
    exercise?.let { exercise ->
        val value: Float = exercise.progress / exercise.limit
        Row {
            Text(text = "${exercise.condition.name}\nLVL: ${exercise.LVL}",
                Modifier
                    .padding(10.dp)
                    .width(70.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .width(150.dp)
                    .height(7.dp)
                    .clip(
                        RoundedCornerShape(50)
                    ),
                color = Color.Red,
                progress = value,
                backgroundColor = Color.Black
            )
            Text(text = "Result ${exercise.result}", Modifier.padding(10.dp))
        }
    }
}
