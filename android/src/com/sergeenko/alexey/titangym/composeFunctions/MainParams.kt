package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygdx.game.Player
import com.mygdx.game.model.Day
import com.mygdx.game.model.items.supplements.SupplementItem
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.items.PlayersActiveItem
import com.sergeenko.alexey.titangym.linearProgressModifier
import com.sergeenko.alexey.titangym.round5Modifier

@Composable
fun MainParams(player: Player) {
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
        val tirednessProgress = playerExerciseManager.tiredness.currentAmount.toFloat() / 100f

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
                    CurrentExercise(tirednessProgress, progressColor = Color.Green)
                }
            }
            Spacer(height = 5.dp)
            Row(
                modifier = round5Modifier.width(215.dp)
            ) {
                RoundBackgroundText(text = stringResource(id = R.string.money_postfix, playersMoney))
                RoundBackgroundText(text = stringResource(id = R.string.perk_coins_postfix, perksCoins))
            }
            Spacer(height = 5.dp)
            if(playerActiveItemList.isNotEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(30.dp),
                    modifier = round5Modifier
                        .width(205.dp)
                ) {
                    items(playerActiveItemList){
                        PlayersActiveItem(it)
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
        modifier = round5Modifier
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
