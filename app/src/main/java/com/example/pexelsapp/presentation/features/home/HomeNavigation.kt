package com.example.pexelsapp.presentation.features.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.pexelsapp.data.utils.SCREEN_TRANSITION_DURATION_MS
import com.example.pexelsapp.domain.Photo
import com.example.pexelsapp.presentation.features.bookmarks.bookmarksRoute

const val homeRoute = "home"

fun NavHostController.navigateToHome(navOptions: NavOptions? = null){
    val currentEntryId =
        this.currentDestination?.id ?:
        this.graph.findStartDestination().id

    this.navigate(homeRoute){
        popUpTo(currentEntryId){
            saveState = currentDestination?.route == bookmarksRoute
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.homeScreen(navigateToDetails: (Photo) -> Unit){
    composable(
        route = homeRoute,
        enterTransition = {
            when (initialState.destination.route) {
                bookmarksRoute ->
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(SCREEN_TRANSITION_DURATION_MS)
                    )
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                bookmarksRoute ->
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(SCREEN_TRANSITION_DURATION_MS)
                    )
                else -> null
            }
        }
    ) {
        val homeViewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(homeViewModel = homeViewModel, onCardClick = navigateToDetails)
    }
}
