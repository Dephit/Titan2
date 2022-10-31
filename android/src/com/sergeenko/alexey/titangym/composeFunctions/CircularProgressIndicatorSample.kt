package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressIndicatorSample() {
        Column(
            modifier = dialogModifier()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer()
                CircularProgressIndicator()
                Spacer()
            }
        }
}