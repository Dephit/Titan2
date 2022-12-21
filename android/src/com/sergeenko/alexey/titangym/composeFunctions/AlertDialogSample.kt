package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource

import com.mygdx.game.interfaces.OnClickCallback
import com.sergeenko.alexey.titangym.R

@Composable
fun AlertDialogSample(
    title: String,
    subtitle: String,
    agreeText: String = stringResource(id = R.string.ok),
    closeText: String = stringResource(id = R.string.close),
    onClose: () -> Unit,
    onAgree: () -> Unit,
    state: Boolean,
) {
    if (state) {
        Column {
            AlertDialog(
                onDismissRequest = onClose,
                title = {
                    Text(text = title)
                },
                text = {
                    Text(subtitle)
                },
                confirmButton = {
                    Button(onClick = onAgree) {
                        Text(agreeText)
                    }
                },
                dismissButton = {
                    Button(onClick =onClose) {
                        Text(closeText)
                    }
                }
            )
        }
    }
}

