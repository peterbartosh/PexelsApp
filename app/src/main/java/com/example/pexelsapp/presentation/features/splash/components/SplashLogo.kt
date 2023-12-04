package com.example.pexelsapp.presentation.features.splash.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.presentation.theme.Red

@Composable
fun SplashLogo(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit
) {

    Box(modifier = modifier.size(240.dp)) {

        AnimatedImage(onFinish)

        AnimatedText()
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashLogoPreview() {
    SplashLogo(Modifier.background(Red)) {}
}