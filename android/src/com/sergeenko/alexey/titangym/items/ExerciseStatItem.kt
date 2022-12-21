package com.sergeenko.alexey.titangym.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mygdx.game.Exercise
import com.mygdx.game.PlayerCondition
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.clipRound
import com.sergeenko.alexey.titangym.composeFunctions.Spacer
import com.sergeenko.alexey.titangym.composeFunctions.shadow

//Todo Make normal texts
@Composable
fun ExerciseStatItem(
    exercise: Exercise
) {
    val value: Float = exercise.progress / exercise.limit
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        val title = stringResource(id = R.string.exercise_stat_level, getExerciseName(exercise.condition), exercise.LVL)
        Text(text = title, textAlign = TextAlign.Start, style = shadow)
        Spacer(width = 10.dp)
        Box(){
            Box(modifier = Modifier.clip(RoundedCornerShape(25.dp))
                .width(200.dp)
                .border(5.dp, Color.DarkGray, shape = RoundedCornerShape(25.dp))
                .height(10.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .width(200.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .height(10.dp).padding(2.dp),
                color = Color.White,
                progress = value,
                backgroundColor = Color.Gray
            )
        }

    }
    Text(text = stringResource(id = R.string.best_result, exercise.bestResult), textAlign = TextAlign.Start, style = shadow)
    Text(text = getExerciseDescription(exercise.condition), textAlign = TextAlign.Start, style = shadow)
}

@Composable
fun getExerciseDescription(condition: PlayerCondition?): String {
    val id = when(condition){
        PlayerCondition.squat -> R.string.squat_description
        PlayerCondition.bench -> R.string.bench_description
        PlayerCondition.deadlift -> R.string.deadlift_description
        else -> R.string.exrcise
    }
    return stringResource(id = id)
}

@Composable
fun getExerciseName(condition: PlayerCondition?): String {
    val id = when(condition){
        PlayerCondition.squat -> R.string.squat
        PlayerCondition.bench -> R.string.bench
        PlayerCondition.deadlift -> R.string.deadlift
        else -> R.string.exrcise
    }
    return stringResource(id = id)
}
