package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.mygdx.game.Exercise
import com.mygdx.game.Player
import com.mygdx.game.model.items.Item
import com.sergeenko.alexey.titangym.clipRound
import com.sergeenko.alexey.titangym.items.ExerciseStatItem
import com.sergeenko.alexey.titangym.linearProgressModifier
import com.sergeenko.alexey.titangym.round5Modifier
import com.sergeenko.alexey.titangym.R as R

@OptIn(ExperimentalUnitApi::class)
@Composable
fun DrawStatsMenu(player: Player) {
    val playerExerciseManager = player.exerciseManager

    val healthProgress = playerExerciseManager.health.currentAmount.toFloat() / 100f
    val energyProgress = playerExerciseManager.energy.currentAmount.toFloat() / 100f
    val tirednessProgress = playerExerciseManager.tiredness.currentAmount.toFloat() / 100f

    val stats = listOf(player.exerciseManager.squatExr, player.exerciseManager.bench, player.exerciseManager.deadlift)

    LazyColumn(
        modifier = blockModifier
            .fillMaxWidth(0.8f)
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
        item {
            Column(
                modifier = blockModifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.tiredness_bar), style = shadow)
                    StatExercise(tirednessProgress, progressColor = Color.Green, modifierColor = Modifier)
                }
                Text(text = stringResource(id = R.string.tiredness_bar_description), textAlign = TextAlign.Start, style = shadow)
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

val blockModifier = Modifier
    .clip(RoundedCornerShape(25.dp))
    .background(Color.Gray)
    .border(5.dp, Color.DarkGray, shape = RoundedCornerShape(25.dp))
    .padding(10.dp)