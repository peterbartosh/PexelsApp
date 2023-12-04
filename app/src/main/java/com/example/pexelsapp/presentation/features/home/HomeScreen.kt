package com.example.pexelsapp.presentation.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pexelsapp.data.utils.UiState
import com.example.pexelsapp.domain.Photo
import com.example.pexelsapp.presentation.components.LinearLoadingRow
import com.example.pexelsapp.presentation.features.home.components.HomePhotosLazyColumn
import com.example.pexelsapp.presentation.features.home.components.FeaturedCollectionsRow
import com.example.pexelsapp.presentation.features.home.components.SearchBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onCardClick: (Photo) -> Unit
) {

    val uiState = homeViewModel.uiState.collectAsState()

    val collectionsScrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var searchState by remember {
        mutableStateOf("")
    }

    val collectionPicked by remember(homeViewModel.lastPickedCollectionIndex) {
        mutableStateOf(homeViewModel.lastPickedCollectionIndex != -1)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 12.dp)) {

        SearchBar(
            searchState = searchState,
            onValueChange = { input -> searchState = input },
            onDone = { localeTag ->
                searchState = searchState.trim()
                val matchedCollection = homeViewModel.collections.find {
                    it.title.equals(searchState, true)
                }
                scope.launch {
                    if (matchedCollection != null) {
                        homeViewModel.getCollectionPhotos(matchedCollection.id)
                        homeViewModel.pickCollection(matchedCollection).join()
                        collectionsScrollState.animateScrollToItem(0)

                    } else {
                        homeViewModel.searchPhotos(searchState, localeTag)
                        if (homeViewModel.lastPickedCollectionIndex != -1) {
                            launch {
                                collectionsScrollState.animateScrollToItem(homeViewModel.lastPickedCollectionIndex)
                            }.join()
                            homeViewModel.unpickCollection()
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        LinearLoadingRow(isLoading = uiState.value is UiState.Loading)

        FeaturedCollectionsRow(
            scrollState = collectionsScrollState,
            collections = homeViewModel.collections,
            collectionPicked = collectionPicked,
            uploadNextPage = homeViewModel::uploadMoreFeaturesCollections,
        ){ collection ->
            scope.launch {
                searchState = collection.title
                homeViewModel.pickCollection(collection).join()
                homeViewModel.getCollectionPhotos(collection.id)
                collectionsScrollState.animateScrollToItem(0)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        HomePhotosLazyColumn(
            uiStateValue = uiState.value,
            photos = homeViewModel.photosState,
            onPhotoClick = onCardClick,
            tryAgain = homeViewModel::refresh,
            getCuratedPhotos = {
                searchState = ""
                homeViewModel.getCuratedPhotos()
            },
            uploadNextPage = homeViewModel::uploadMorePhotos
        )
    }
}