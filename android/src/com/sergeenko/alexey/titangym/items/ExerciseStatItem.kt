package com.sergeenko.alexey.titangym.items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mygdx.game.Exercise
import com.mygdx.game.Player
import com.mygdx.game.model.items.Item
import com.sergeenko.alexey.titangym.linearProgressModifier
import com.sergeenko.alexey.titangym.round5Modifier

//Todo Make normal texts
@Composable
fun ExerciseStatItem(exercise: Exercise) {
    val value: Float = exercise.progress / exercise.limit
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "${exercise.condition.name}\nLVL: ${exercise.LVL}",
            Modifier.padding(10.dp)
        )
        LinearProgressIndicator(
            modifier = Modifier.linearProgressModifier(),
            color = Color.Red,
            progress = value,
            backgroundColor = Color.Black
        )
        Text(text = "Result ${exercise.result}", Modifier.padding(10.dp))
    }
}