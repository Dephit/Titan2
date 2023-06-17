package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.mygdx.game.*
import com.mygdx.game.interfaces.*

val language: Language
    get() = Preffics.getInstance().language

@Composable
fun ColumnScope.CloseButton(onClose: OnClickCallback) {
    MyButton(
        onClick = onClose::call,
        modifier = Modifier.align(Alignment.End),
        text = language.cancel,
    )
}

@Composable
fun RowScope.CloseButton(onClose: OnClickCallback) {
    MyButton(
        onClick = onClose::call,
        text = language.cancel
    )
}


@Composable
fun MyButton(onClick: OnClickCallback?, text: String, modifier: Modifier = Modifier) {
    return Button(
        colors = ButtonDefaults
            .buttonColors(
                backgroundColor = Color.DarkGray,
                contentColor = Color.White
            ),
        onClick = {
            onClick?.call(null)
        },
        modifier = modifier.padding(10.dp)
    ) {
        Text(text = text)
    }
}
