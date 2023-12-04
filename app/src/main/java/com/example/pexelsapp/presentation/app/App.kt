package com.example.pexelsapp.presentation.app

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.pexelsapp.presentation.features.bookmarks.bookmarksRoute
import com.example.pexelsapp.presentation.features.details.detailsRoute
import com.example.pexelsapp.presentation.features.home.homeRoute
import com.example.pexelsapp.presentation.features.splash.splashRoute
import com.example.pexelsapp.presentation.theme.PexelsAppTheme
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {

    var showBottomBar by remember {
        mutableStateOf(false)
    }

    var selectedNavItemIndex by remember {
        mutableStateOf(0)
    }

    val navController = rememberNavController()

    navController.addOnDestinationChangedListener{ cont, dest, args ->
        showBottomBar = dest.route !in listOf(splashRoute, detailsRoute)
        selectedNavItemIndex =
            when (dest.route) {
                homeRoute -> 0
                bookmarksRoute -> 1
                else -> selectedNavItemIndex
            }
    }

    PexelsAppTheme {
        Scaffold(
            bottomBar = {
                if (showBottomBar)
                    BottomBar(selectedNavItemIndex) { route ->

                        val currentEntryId =
                            navController.currentDestination?.id ?:
                            navController.graph.findStartDestination().id

                        navController.navigate(route){
                            popUpTo(currentEntryId){
                                saveState = true
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                    } else Box {}
            },

        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AppNavHost(navController)
            }
        }
    }
}