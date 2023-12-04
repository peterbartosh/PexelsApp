package com.example.pexelsapp.presentation.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pexelsapp.presentation.features.splash.components.SplashLogo
import com.example.pexelsapp.presentation.theme.Red
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToMain: () -> Unit) {

    var animationFinished by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = animationFinished){
        if (animationFinished) {
            navigateToMain()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SplashLogo{
            animationFinished = true
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen {}
}