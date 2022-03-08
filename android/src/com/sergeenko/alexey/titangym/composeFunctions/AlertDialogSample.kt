package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource

import com.mygdx.game.interfaces.OnCLickCallback
import com.sergeenko.alexey.titangym.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AlertDialogSample(
    title: String,
    subtitle: String,
    agreeText: String = stringResource(id = R.string.ok),
    closeText: String = stringResource(id = R.string.close),
    onClose: OnCLickCallback,
    onAgree: OnCLickCallback,
    state: StateFlow<Boolean>,
) {
    Column {
        //val openDialog = remember { mutableStateOf(false)  }


        if (state.value) {
            AlertDialog(
                onDismissRequest = {
                    //openDialog.value = false
                    onClose.call(null)
                },
                title = {
                    Text(text = title)
                },
                text = {
                    Text(subtitle)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            //openDialog.value = false
                            onAgree.call(null)
                        }) {
                        Text(agreeText)
                    }
                },
                dismissButton = {
                    Button(

                        onClick = {
                            //openDialog.value = false
                            onClose.call(null)
                        }) {
                        Text(closeText)
                    }
                }
            )
        }
    }
}

