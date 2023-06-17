package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.mygdx.game.*
import com.mygdx.game.model.*
import com.mygdx.game.model.items.*
import com.mygdx.game.model.items.supplements.*
import com.sergeenko.alexey.titangym.*
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.items.*

@Composable
fun MainParams(
    player: Player,
    onActiveItemClick: (Item) -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top
    ){

        val playerInventoryManager = player.inventoryManager
        val playerExerciseManager = player.exerciseManager
        val day = player.day

        val healthProgress = playerExerciseManager.health.currentAmount.toFloat() / 100f

        val energyProgress = playerExerciseManager.energy.currentAmount.toFloat() / 100f

        val playersMoney = playerInventoryManager.pocket.money
        val perksCoins = playerInventoryManager.perkPocket.money

        val playerActiveItemList =  listOf(
            playerInventoryManager.supplements.items.filter { it is SupplementItem && it.timeInUseLeft < it.timeWillBeLast },
            playerInventoryManager.equipmentContainer.items
        ).flatten()


        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                DayItem(day)
                Spacer(width = 5.dp)
                Column(
                    modifier = round5Modifier
                ) {
                    CurrentExercise(healthProgress, progressColor = Color.Red)
                    CurrentExercise(energyProgress, progressColor = Color.Yellow)
                    //CurrentExercise(tirednessProgress, progressColor = Color.Green)
                }
            }
            Spacer(height = 5.dp)
            Row(
                modifier = round5Modifier.width(218.dp)
            ) {
                RoundBackgroundText(text = stringResource(id = R.string.money_postfix, playersMoney))
                RoundBackgroundText(text = stringResource(id = R.string.perk_coins_postfix, perksCoins))
            }
            Spacer(height = 5.dp)
            if(playerActiveItemList.isNotEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(30.dp),
                    modifier = round5Modifier
                        .width(219.dp)
                ) {
                    items(playerActiveItemList){
                        PlayersActiveItem(it, onActiveItemClick)
                    }
                }
            }
        }

    }
}

@Composable
fun DayItem(day: Day) {
    val currentDay = day.currentDay
    val currentTimeProgress = day.currentTime / 24f

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = round5Modifier.height(25.dp)
    ) {
        Text(text = "Day $currentDay", fontSize = 15.sp, color = Color.Black, textAlign = TextAlign.Center)
        LinearProgressIndicator(
            modifier = Modifier
                .width(40.dp)
                .linearProgressModifier(),
            color = Color.Blue,
            progress = currentTimeProgress,
            backgroundColor = Color.Black
        )
    }
}
