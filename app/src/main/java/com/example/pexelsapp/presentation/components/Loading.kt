package com.example.pexelsapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.presentation.theme.Red

@Composable
fun CircleLoadingBox(isLoading: Boolean, size: Dp = 20.dp, strokeWidth: Dp = 2.dp) {
    if (isLoading)
        Box(modifier = Modifier.size(size)) {
            CircularProgressIndicator(
                modifier = Modifier.matchParentSize(),
                color = Red,
                strokeWidth = strokeWidth
            )
        }
}

@Composable
fun LinearLoadingRow(isLoading: Boolean) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(4.dp)) {
        if (isLoading)
            LinearProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.tertiary,
                trackColor = MaterialTheme.colorScheme.primaryContainer
            )
    }
}