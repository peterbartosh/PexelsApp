package com.example.pexelsapp.presentation.features.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val splashRoute = "splash"

fun NavGraphBuilder.splashScreen(navigateToMain: () -> Unit){
    composable(splashRoute) {
        SplashScreen(navigateToMain = navigateToMain)
    }
}