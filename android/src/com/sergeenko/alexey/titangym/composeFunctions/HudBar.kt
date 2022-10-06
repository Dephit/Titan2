package com.sergeenko.alexey.titangym.composeFunctions

import android.content.res.AssetManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygdx.game.Exercise
import com.mygdx.game.Player
import com.mygdx.game.managers.NotificationManager
import com.mygdx.game.model.items.supplements.SupplementItem
import com.sergeenko.alexey.titangym.assetsToBitmap

@Composable
@Preview
fun HudBar(
    am: AssetManager,
    player: Player
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        MainParams(am, player, player.isInExercise != null)
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
            notificationManager.notificationList.filter { it.show }.sortedByDescending { it.showTime }.take(5).forEach {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainParams(am: AssetManager, player: Player, isInExercise: Boolean) {
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
            LazyVerticalGrid(
                cells = GridCells.Adaptive(30.dp),
                modifier = Modifier.width(150.dp).height(200.dp)
            ) {
                listOf(
                    player.inventoryManager.supplements.items.filter { it is SupplementItem && it.timeInUseLeft < it.timeWillBeLast },
                    player.inventoryManager.equipmentContainer.items
                ).flatten().forEach { sup ->
                    item {
                        am.assetsToBitmap(getItemPath(sup.styleName))
                            ?.asImageBitmap()?.let {
                                Column(modifier = Modifier.background(Color.White)) {
                                    Image(bitmap = it,
                                        contentDescription = "Localized description",
                                        contentScale = ContentScale.FillBounds,
                                        modifier = Modifier
                                            .width(30.dp)
                                            .height(30.dp)
                                            .clickable {}
                                    )
                                    if(sup is SupplementItem){
                                        LinearProgressIndicator(
                                            modifier = Modifier
                                                .padding(1.dp)
                                                .fillMaxWidth()
                                                .height(4.dp)
                                                .clip(
                                                    RoundedCornerShape(50)
                                                )
                                            ,
                                            color = Color.Black,
                                            progress = 1f -(sup.timeInUseLeft / sup.timeWillBeLast.toFloat()),
                                            backgroundColor = Color.Gray
                                        )
                                    }
                                }
                            }
                    }
                }
            }
        }

    }
}
