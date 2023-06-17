package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.sergeenko.alexey.titangym.fragments.ComposeState

@Composable
fun DrawProgressBar(state: ComposeState.ShowProgressBar, onClose: () -> Unit) {
    val progress = remember { mutableStateOf(0f) }

    if(progress.value < 1f && state.isConditionSatisfied()){
        ProgressBar(
            title = state.title,
            onClose = onClose,
            state = progress.value,
        ){
            progress.value += 0.1f
        }

    }else{
        state.done()
        onClose()
    }
}
