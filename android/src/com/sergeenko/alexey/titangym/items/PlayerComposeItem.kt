package com.sergeenko.alexey.titangym.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.enums.Comp
import com.sergeenko.alexey.titangym.R

@Composable
fun TableHeaderComposeItem(){
    return Box {
        Row {
            PlayerText(stringResource(id = R.string.rnk))
            PlayerText(stringResource(id = R.string.player_name), modifier = Modifier.weight(2f))
            PlayerText(stringResource(id = R.string.sqt), modifier = Modifier.weight(3f))
            PlayerText(stringResource(id = R.string.bp), modifier = Modifier.weight(3f))
            PlayerText(stringResource(id = R.string.dl), modifier = Modifier.weight(3f))
            PlayerText(stringResource(id = R.string.total))
        }
    }
}

@Composable
fun PlayerComposeItem(
    index: Int,
    status: Comp,
    player: CompetitionOpponent,
){
    return Box {
        Row(modifier = Modifier
            .background(color = colorResource(
                if(player.name == "player")
                    R.color.color_player
                else
                    R.color.color_white
            ))
        ) {
            PlayerText(index.toString())
            PlayerText(player.name, modifier = Modifier.weight(2f))
            WeightSection(player.squat.firstAttempt,status, 1)
            WeightSection(player.squat.secondAttempt,status, 2)
            WeightSection(player.squat.thirdAttempt,status, 3)
            WeightSection(player.bench.firstAttempt,status, 4)
            WeightSection(player.bench.secondAttempt,status, 5)
            WeightSection(player.bench.thirdAttempt,status, 6)
            WeightSection(player.deadlift.firstAttempt,status, 7)
            WeightSection(player.deadlift.secondAttempt,status, 8)
            WeightSection(player.deadlift.thirdAttempt,status, 9)
            PlayerText(player.getTotal().toString())
        }
    }
}

@Composable
private fun RowScope.WeightSection(exercise: CompetitionOpponent.Attempt?, status: Comp, set: Int) {
    var text = exercise?.weight.toString()
    val color = when {
        set == status.attempt -> {
            R.color.color_white
        }
        set > status.attempt -> {
            text = "-"
            R.color.current_attempt
        }
        exercise?.isGood == true -> {
            R.color.good_attempt
        }
        exercise?.isGood == false -> {
            R.color.bad_attempt
        }
        else -> R.color.current_attempt
    }
    return PlayerText(
        text,
        modifier = Modifier.background(colorResource(color))
    )
}

@Composable
private fun RowScope.PlayerText(text: String, modifier: Modifier = Modifier, weight: Float = 1f) {
    Text(
        modifier = modifier
            .weight(weight)
            .border(width = 1.dp, colorResource(android.R.color.holo_blue_dark)),
        text = text,
        maxLines = 1,
        textAlign = TextAlign.Center)
}
