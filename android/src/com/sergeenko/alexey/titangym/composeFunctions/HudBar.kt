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
import androidx.compose.ui.unit.dp
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
        MainParams(player)
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
    if(bar != null){
        LinearProgressIndicator(
            modifier = Modifier
                .width(150.dp)
                .height(15.dp)
                .clip(
                    RoundedCornerShape(50)
                ),
            color = Color.White,
            progress = bar.statBar.currentAmount / bar.limit,
            backgroundColor = Color.Black
        )
    }
}

@Composable
fun MainParams(player: Player) {
    Row(
        modifier = Modifier.padding(end = 30.dp)
    ) {
        Spacer(
            Modifier
                .fillMaxWidth()
                .weight(1f))
        Column {
            LinearProgressIndicator(
                modifier = Modifier
                    .width(150.dp)
                    .height(15.dp)
                    .clip(
                        RoundedCornerShape(50)
                    )
                ,
                color = Color.Red,
                progress = player.healthBar.currentAmount / 100f,
                backgroundColor = Color.Black
            )
            Spacer(
                Modifier.height(10.dp)
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .width(150.dp)
                    .height(15.dp)
                    .clip(
                        RoundedCornerShape(50)
                    )
                ,
                color = Color.Yellow,
                progress = player.energyBar.currentAmount / 100f,
                backgroundColor = Color.Black
            )
            Spacer(
                Modifier.height(10.dp)
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .width(150.dp)
                    .height(15.dp)
                    .clip(
                        RoundedCornerShape(50)
                    )
                ,
                color = Color.Green,
                progress = player.tirednessBar.currentAmount / 100f,
                backgroundColor = Color.Black
            )
            Spacer(
                Modifier.height(10.dp)
            )
        }

    }
}
