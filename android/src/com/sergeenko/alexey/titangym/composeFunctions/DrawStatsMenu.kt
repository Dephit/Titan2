package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.mygdx.game.*
import com.sergeenko.alexey.titangym.*
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.items.*

@Composable
fun DrawStatsMenu(
    player: Player,
    widthFraction: Float = 0.8f
) {
    val playerExerciseManager = player.exerciseManager

    val healthProgress = playerExerciseManager.health.currentAmount.toFloat() / 100f
    val energyProgress = playerExerciseManager.energy.currentAmount.toFloat() / 100f

    val stats = listOf(player.exerciseManager.squatExr, player.exerciseManager.bench, player.exerciseManager.deadlift)

    LazyColumn(
        modifier = blockModifier
            .fillMaxWidth(widthFraction)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item {
            Column(
                modifier = blockModifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.health_bar), style = shadow)
                    StatExercise(healthProgress, progressColor = Color.Red, modifierColor = Modifier)
                }
                Text(text = stringResource(id = R.string.health_bar_description), textAlign = TextAlign.Start, style = shadow)
            }
            Spacer(height = 5.dp)
        }
        item {
            Column(
                modifier = blockModifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.energy_bar), style = shadow)
                    StatExercise(energyProgress, progressColor = Color.Yellow, modifierColor = Modifier)
                }
                Text(text = stringResource(id = R.string.energy_bar_description), textAlign = TextAlign.Start, style = shadow)
            }
            Spacer(height = 5.dp)
        }

        items(stats){
            Column(
                modifier = blockModifier
            ) {
                ExerciseStatItem(exercise = it)
            }
            Spacer(height = 5.dp)
        }
        item {
            Column(
                modifier = blockModifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.days_in_the_game, player.day.currentDay), textAlign = TextAlign.Start, style = shadow)
                Text(text = stringResource(id = R.string.current_money, player.inventoryManager.pocket.money), textAlign = TextAlign.Start, style = shadow)
                //todo add total money
                Text(text = stringResource(id = R.string.total_money, player.inventoryManager.pocket.money), textAlign = TextAlign.Start, style = shadow)
                Text(text = stringResource(id = R.string.current_perk_coins, player.inventoryManager.perkPocket.money), textAlign = TextAlign.Start, style = shadow)
                Text(text = stringResource(id = R.string.current_perks, player.inventoryManager.perkContainer.items.size), textAlign = TextAlign.Start, style = shadow)
            }
        }
    }
}

val shadow = TextStyle(shadow = Shadow(
    color = Color.DarkGray,
    offset = Offset(1.0f, 1.0f),
    blurRadius = 3f
)
)

