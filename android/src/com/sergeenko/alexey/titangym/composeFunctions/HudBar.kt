package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygdx.game.Exercise
import com.mygdx.game.Player
import com.mygdx.game.managers.NotificationManager

@Composable
@Preview
fun HudBar(
    player: Player
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        MainParams(player, player.isInExercise != null)
        CurrentExercise(player.isInExercise)
        NotificationList(player.notificationManager)
    }
}

@Composable
fun NotificationList(notificationManager: NotificationManager) {
    Row(
        modifier = Modifier.padding(end = 30.dp)
    ) {
        Spacer(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Column() {
            Spacer(
                Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            notificationManager.notificationList.sortedByDescending { it.showTime }.forEach {
                if(it.show) {
                    Text(
                        text = it.message,
                        modifier = Modifier
                            .background(color = Color.White)
                            .clip(RoundedCornerShape(50))
                            .padding(3.dp),
                        color = Color.Black
                    )
                    Spacer(
                        Modifier.height(3.dp)
                    )
                }
            }
        }
    }
    notificationManager.countNotification()
}

@Composable
fun CurrentExercise(bar: Exercise?) {
    val value: Float
    val height: Dp
    if(bar != null){
        value = bar.statBar.currentAmount / bar.limit
        height = 5.dp
    }else {
        value = 0f
        height = 3.dp
    }
    Column() {
        Spacer(
            Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        LinearProgressIndicator(
            modifier = Modifier
                .width(150.dp)
                .height(height)
                .clip(
                    RoundedCornerShape(50)
                ),
            color = Color.White,
            progress = value,
            backgroundColor = Color.Black
        )
    }
}

@Composable
fun MainParams(player: Player, isInExercise: Boolean) {
    Row(
        modifier = Modifier.padding(end = 30.dp)
    ) {
        val barHeight = if(isInExercise) 15.dp else 5.dp
        Spacer(
            Modifier
                .fillMaxWidth()
                .weight(1f))
        Text(text = "${player.day.currentDay}d", fontSize = 30.sp, color = Color.White)
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            LinearProgressIndicator(
                modifier = Modifier
                    .width(150.dp)
                    .height(barHeight)
                    .clip(
                        RoundedCornerShape(50)
                    )
                ,
                color = Color.Red,
                progress = player.exerciseManager.health.currentAmount / 100f,
                backgroundColor = Color.Black
            )
            Spacer(
                Modifier.height(10.dp)
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .width(150.dp)
                    .height(barHeight)
                    .clip(
                        RoundedCornerShape(50)
                    )
                ,
                color = Color.Yellow,
                progress = player.exerciseManager.energy.currentAmount / 100f,
                backgroundColor = Color.Black
            )
            Spacer(
                Modifier.height(10.dp)
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .width(150.dp)
                    .height(barHeight)
                    .clip(
                        RoundedCornerShape(50)
                    )
                ,
                color = Color.Green,
                progress = player.exerciseManager.tiredness.currentAmount / 100f,
                backgroundColor = Color.Black
            )
            Spacer(
                Modifier.height(10.dp)
            )
        }

    }
}
