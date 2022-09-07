package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mygdx.game.Player

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
        val bar = player.isInExercise

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
                if(bar != null){
                    LinearProgressIndicator(
                        modifier = Modifier
                            .width(150.dp)
                            .height(15.dp)
                            .clip(
                                RoundedCornerShape(50)
                            )
                        ,
                        color = Color.White,
                        progress = bar.statBar.currentAmount / 100f,
                        backgroundColor = Color.Black
                    )
                }
            }

        }
    }
}