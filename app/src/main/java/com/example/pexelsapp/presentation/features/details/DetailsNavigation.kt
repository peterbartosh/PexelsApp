package com.example.pexelsapp.presentation.features.details

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.pexelsapp.data.utils.SCREEN_TRANSITION_DURATION_MS
import com.example.pexelsapp.domain.Photo

const val detailsRoute = "details"
const val PHOTO_ID_KEY = "photo"
const val IS_NETWORK_REQUEST_KEY = "is_net_req"

fun NavHostController.navigateToDetails(
    photo: Photo,
    isNetworkRequest: Boolean,
    navOptions: NavOptions? = null
){
    this.navigate(detailsRoute, navOptions)
    this.currentBackStackEntry?.savedStateHandle?.set(PHOTO_ID_KEY, photo.id)
    this.currentBackStackEntry?.savedStateHandle?.set(IS_NETWORK_REQUEST_KEY, isNetworkRequest)
}

fun NavGraphBuilder.detailsScreen(
    popBack: () -> Unit,
    navigateHome: () -> Unit
){
    composable(
        route = detailsRoute,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(SCREEN_TRANSITION_DURATION_MS)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(SCREEN_TRANSITION_DURATION_MS)
            )
        }
    ) { backStackEntry ->

        val detailsViewModel = hiltViewModel<DetailsViewModel>()
        val photoId = backStackEntry.savedStateHandle.get<Int>(PHOTO_ID_KEY)
        val isNetworkRequest = backStackEntry.savedStateHandle.get<Boolean>(IS_NETWORK_REQUEST_KEY) ?: true

        DetailsScreen(
            detailsViewModel = detailsViewModel,
            photoId = photoId,
            isNetworkRequest = isNetworkRequest,
            onBackPressed = popBack,
            onExploreClick = navigateHome
        )

    }
}