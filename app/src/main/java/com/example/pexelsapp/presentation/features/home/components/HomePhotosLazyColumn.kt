package com.example.pexelsapp.presentation.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.pexelsapp.data.utils.ErrorType
import com.example.pexelsapp.data.utils.PHOTOS_PER_PAGE
import com.example.pexelsapp.data.utils.UiState
import com.example.pexelsapp.domain.Photo
import com.example.pexelsapp.presentation.components.ErrorOccurred
import com.example.pexelsapp.presentation.components.PhotosLazyList
import com.example.pexelsapp.presentation.components.SearchResultEmpty

@Composable
fun HomePhotosLazyColumn(
    uiStateValue: UiState<Boolean>,
    photos: List<Photo>,
    onPhotoClick: (Photo) -> Unit,
    tryAgain: () -> Unit,
    getCuratedPhotos: () -> Unit,
    uploadNextPage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiStateValue !is UiState.Failure) {
            PhotosLazyList(
                photos = if (uiStateValue is UiState.Success)
                    photos
                else
                    (1..PHOTOS_PER_PAGE).map { Photo(it, "alt", 0, 0, "$it", "") },    // to display shimmers
                showAuthorName = false,
                isLoading = uiStateValue is UiState.Loading,
                onPhotoClick = onPhotoClick,
                uploadNextPage = uploadNextPage
            )
        } else {
            when (uiStateValue.errorType) {
                ErrorType.ConnectionLost -> ErrorOccurred(tryAgain)
                ErrorType.HttpError -> ErrorOccurred(tryAgain)
                ErrorType.EmptyResult -> SearchResultEmpty(getCuratedPhotos)
            }
        }
    }
}
