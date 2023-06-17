package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import com.mygdx.game.interfaces.OnClickCallback
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.featureGameScreen.models.DialogState

@Composable
fun DialogComposable(dialogState: DialogState){
    val context = LocalContext.current
    when(dialogState){
        DialogState.None -> { }
        is DialogState.ShowDialog -> {
            AlertDialogSample(
                title = dialogState.titleRes,
                subtitle = dialogState.subtitleRes,
                agreeText = context.getString(R.string.ok),
                closeText = context.getString(R.string.close),
                onAgree = dialogState.onAgree,
                onClose = dialogState.onClose
            )
        }
        is DialogState.ShowItemDialog -> {
            AlertDialogSample(
                title = dialogState.item.title,
                subtitle = dialogState.item.description,
                agreeText = context.getString(R.string.ok),
                closeText = context.getString(R.string.close),
                onAgree = dialogState.onAgree,
                onClose = dialogState.onClose
            )
        }
        is DialogState.ShowDialogWithCustomButtonText -> {
            AlertDialogSample(
                title = dialogState.titleRes,
                subtitle = dialogState.subtitleRes,
                agreeText = dialogState.agreeText,
                closeText = dialogState.closeText,
                onAgree = dialogState.onAgree,
                onClose = dialogState.onClose
            )
        }
    }
}

@Composable
fun AlertDialogSample(
    title: String,
    subtitle: String,
    agreeText: String = stringResource(id = R.string.ok),
    closeText: String = stringResource(id = R.string.close),
    onClose: () -> Unit,
    onAgree: () -> Unit
) {
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

