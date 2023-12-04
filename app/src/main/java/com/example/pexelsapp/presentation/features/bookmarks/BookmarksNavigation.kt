package com.example.pexelsapp.presentation.features.bookmarks

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pexelsapp.data.utils.SCREEN_TRANSITION_DURATION_MS
import com.example.pexelsapp.domain.Photo
import com.example.pexelsapp.presentation.features.home.homeRoute

const val bookmarksRoute = "bookmarks"

fun NavGraphBuilder.bookmarksScreen(
    navigateToHome: () -> Unit,
    navigateToDetails: (Photo) -> Unit
){
    composable(
        route = bookmarksRoute,
        enterTransition = {
            when (initialState.destination.route) {
                homeRoute ->
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(SCREEN_TRANSITION_DURATION_MS)
                    )
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                homeRoute ->
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(SCREEN_TRANSITION_DURATION_MS)
                    )
                else -> null
            }
        },
    ) {
        val bookmarksViewModel = hiltViewModel<BookmarksViewModel>()
        BookmarksScreen(
            bookmarksViewModel,
            onExploreClick = navigateToHome,
            onPhotoClick = navigateToDetails
        )
    }
}