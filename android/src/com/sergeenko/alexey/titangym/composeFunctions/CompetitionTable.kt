package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.model.CompetitionOpponent
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.fragments.ComposeState
import kotlin.random.Random

@Composable
fun CompetitionTable(
    player: Player,
    state: ComposeState.ShowCompetitionTable,
    onClose: ()->Unit
) {
    return DialogBox{
        Column(
            modifier = dialogModifier()
        ) {
            val color = getFlagColor(state.currentSet, player)
            Row {
                Box(modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp)
                    .background(colorResource(id = color)))

                Box(modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp)
                    .background(colorResource(id = color)))

                Box(modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp)
                    .background(colorResource(id = color)))
                Spacer(modifier = Modifier.weight(1f))
                MyButton(
                    onClick = {
                        state.onFirstClick()
                    },
                    stringResource(R.string.scoresheet))
                CloseButton{
                    onClose()
                }
            }
            if(state.currentSet < 10){
                val first = getResult(state.currentSet, player)
                val second = first + first * 0.025
                val third = first + first * 0.05
                val fourth = first + first * 0.075
                Spacer(modifier = Modifier.height(40.dp))
                getPercentages(
                    compStatus = state.currentSet,
                    onFirstClick = {
                        state.onFirstClick()
                        onClose()
                    },
                    first = first,
                    second = second.roundTo2Point5(),
                    third = third.roundTo2Point5(),
                    fourth = fourth.roundTo2Point5()
                )
            }else{
                val text = stringResource(
                    id = R.string.congratulation,
                    {
                        (state.playerList.sortedByDescending { it.getTotal() }.indexOfFirst { it.name == "player" } + 1).toString()
                    },
                    player.compValue.getTotal()
                )
                val textModifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                Text(
                    text = text,
                    modifier = textModifier,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }
        }
    }
}

@Composable
private fun getPercentages(
    compStatus: Int,
    onFirstClick: OnClickCallback?,
    first: Double,
    second: Double,
    third: Double,
    fourth: Double,
) {
    var per1 = 90
    var per2 = 90
    var per3 = 90
    var per4 = 50
    var title = ""
    val textModifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
    when (compStatus) {
        1 -> {
            per1 = 90
            per2 = 60
            per3 = 30
            per4 = 100
            title = stringResource(R.string.squat_set, "1")
        }
        2 -> {
            per1 = 80
            per2 = 40
            per3 = 15
            per4 = 90
            title = stringResource(R.string.squat_set, "2")
        }
        3 ->  {
            per1 = 60
            per2 = 15
            per3 = 5
            per4 = 50
            title = stringResource(R.string.squat_set, "3")
        }
        4 -> {
            per1 = 90
            per2 = 60
            per3 = 30
            per4 = 100
            title = stringResource(R.string.bench_set, "1")
        }
        5 -> {
            per1 = 80
            per2 = 40
            per3 = 15
            per4 = 90
            title = stringResource(R.string.bench_set, "2")
        }
        6 ->  {
            per1 = 60
            per2 = 15
            per3 = 5
            per4 = 50
            title = stringResource(R.string.bench_set, "3")
        }
        7 -> {
            per1 = 90
            per2 = 60
            per3 = 30
            per4 = 100
            title = stringResource(R.string.dl_set, "1")
        }
        8 -> {
            per1 = 80
            per2 = 40
            per3 = 10
            per4 = 90
            title = stringResource(R.string.dl_set, "2")
        }
        9 ->  {
            per1 = 60
            per2 = 15
            per3 = 5
            per4 = 50
            title = stringResource(R.string.dl_set, "3")
        }
    }
    Text(
        text = title,
        modifier = textModifier,
        textAlign = TextAlign.Center,
        fontSize = 30.sp
    )
    Text(
        modifier = textModifier,
        textAlign = TextAlign.Center,
        text = first.toString(),
        fontSize = 30.sp
    )
    Row {
        SetButton(
            first,
            "$per1%",
        ){
            onFirstClick?.call(CompetitionOpponent.Attempt(first, (Random.nextInt(100) < per1)))
        }
        SetButton(
            second,
            "$per2%",
        ){
            onFirstClick?.call(CompetitionOpponent.Attempt(second, (Random.nextInt(100) < per2)))
        }
        SetButton(
            third,
            "$per3%",
        ){
            onFirstClick?.call(CompetitionOpponent.Attempt(third, (Random.nextInt(100) < per3)))
        }
        SetButton(
            fourth,
            "AD($per4%)",
        ){
            onFirstClick?.call(CompetitionOpponent.Attempt(fourth, true))
        }

    }
}

@Composable
private fun RowScope.SetButton(first: Double, per1: String, function: () -> Unit?) {
    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            text = first.toString(),
            textAlign = TextAlign.Center
        )
        MyButton(
            onClick = { function() },
            text = per1,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        )
    }
}

private fun getResult(compStatus: Int, player: Player): Double {
    when (compStatus) {
        1 -> return player.compValue.squat.firstAttempt.weight
        2 -> {
            player.compValue.squat.secondAttempt.weight = player.compValue.squat.firstAttempt.weight +
                    if(player.compValue.squat.firstAttempt.isGood) player.compValue.squat.firstAttempt.weight * 0.025f else 0.0
            return player.compValue.squat.secondAttempt.weight
        }
        3 ->  {
            player.compValue.squat.thirdAttempt.weight = player.compValue.squat.secondAttempt.weight +
                    if(player.compValue.squat.secondAttempt.isGood) player.compValue.squat.secondAttempt.weight * 0.0125f else 0.0
            return player.compValue.squat.thirdAttempt.weight
        }
        4 -> return player.compValue.bench.firstAttempt.weight
        5 -> {
            player.compValue.bench.secondAttempt.weight = player.compValue.bench.firstAttempt.weight +
                    if(player.compValue.bench.firstAttempt.isGood) player.compValue.bench.firstAttempt.weight * 0.025f else 0.0
            return player.compValue.bench.secondAttempt.weight
        }
        6 ->  {
            player.compValue.bench.thirdAttempt.weight = player.compValue.bench.secondAttempt.weight +
                    if(player.compValue.bench.secondAttempt.isGood) player.compValue.bench.secondAttempt.weight * 0.0125f else 0.0
            return player.compValue.bench.thirdAttempt.weight
        }
        7 -> return player.compValue.deadlift.firstAttempt.weight
        8 -> {
            player.compValue.deadlift.secondAttempt.weight = player.compValue.deadlift.firstAttempt.weight +
                    if(player.compValue.deadlift.firstAttempt.isGood) player.compValue.deadlift.firstAttempt.weight * 0.025f else 0.0
            return player.compValue.deadlift.secondAttempt.weight
        }
        9 ->  {
            player.compValue.deadlift.thirdAttempt.weight = player.compValue.deadlift.secondAttempt.weight +
                    if(player.compValue.deadlift.secondAttempt.isGood) player.compValue.deadlift.firstAttempt.weight * 0.0125f else 0.0
            return player.compValue.deadlift.thirdAttempt.weight
        }
        10 -> return 0.0
    }
    return 0.0
}

private fun getFlagColor(currentSet: Int, player: Player): Int {
    when (currentSet) {
        1 -> return R.color.color_white
        2 -> {
            return if(player.compValue.squat.firstAttempt.isGood) R.color.color_green else R.color.color_red
        }
        3 ->  {
            return if(player.compValue.squat.secondAttempt.isGood) R.color.color_green else R.color.color_red
        }
        4 -> return if(player.compValue.squat.thirdAttempt.isGood) R.color.color_green else R.color.color_red
        5 -> {
            return if(player.compValue.bench.firstAttempt.isGood) R.color.color_green else R.color.color_red
        }
        6 ->  {
            return if(player.compValue.bench.secondAttempt.isGood) R.color.color_green else R.color.color_red
        }
        7 -> return if(player.compValue.bench.thirdAttempt.isGood) R.color.color_green else R.color.color_red
        8 -> {
            return if(player.compValue.deadlift.firstAttempt.isGood) R.color.color_green else R.color.color_red
        }
        9 ->  {
            return if(player.compValue.deadlift.secondAttempt.isGood) R.color.color_green else R.color.color_red
        }
        10 -> return if(player.compValue.deadlift.thirdAttempt.isGood) R.color.color_green else R.color.color_red
    }
    return 0
}


fun Double.roundTo2Point5(): Double{
    return Math.round(this * 0.4) / 0.4
}
