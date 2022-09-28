package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mygdx.game.interfaces.OnClickCallback
import com.sergeenko.alexey.titangym.R

@Composable
fun ColumnScope.CloseButton(onClose: OnClickCallback?) {
    MyButton(
        onClick = {
            onClose?.call(null)
        }, stringResource(R.string.close), modifier = Modifier.align(Alignment.End))
}

@Composable
fun RowScope.CloseButton(onClose: OnClickCallback?) {
    MyButton(
        onClick = {
            onClose?.call(null)
        }, stringResource(R.string.close))
}


@Composable
fun MyButton(onClick: OnClickCallback?, text: String, modifier: Modifier = Modifier){
    return Button(
        onClick = {
            onClick?.call(null)
        }, modifier = modifier.padding(10.dp).shadow(10.dp)) {
        Text(text = text)
    }
}