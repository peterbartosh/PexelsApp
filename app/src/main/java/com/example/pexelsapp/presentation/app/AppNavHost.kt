package com.example.pexelsapp.presentation.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.pexelsapp.presentation.features.bookmarks.bookmarksScreen
import com.example.pexelsapp.presentation.features.details.detailsScreen
import com.example.pexelsapp.presentation.features.details.navigateToDetails
import com.example.pexelsapp.presentation.features.home.homeScreen
import com.example.pexelsapp.presentation.features.home.navigateToHome
import com.example.pexelsapp.presentation.features.splash.splashRoute
import com.example.pexelsapp.presentation.features.splash.splashScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = splashRoute
    ){

        splashScreen(navigateToMain = navController::navigateToHome)
        homeScreen(navigateToDetails = { photo -> navController.navigateToDetails(photo, true) })
        bookmarksScreen(
            navigateToHome = navController::navigateToHome,
            navigateToDetails = { photo -> navController.navigateToDetails(photo, false) }
        )
        detailsScreen(popBack = navController::popBackStack, navigateHome = navController::navigateToHome)
    }
}