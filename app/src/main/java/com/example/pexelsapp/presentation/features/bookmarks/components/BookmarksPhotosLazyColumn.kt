package com.example.pexelsapp.presentation.features.bookmarks.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.data.utils.ErrorType
import com.example.pexelsapp.data.utils.UiState
import com.example.pexelsapp.domain.Photo
import com.example.pexelsapp.presentation.components.BookmarksEmpty
import com.example.pexelsapp.presentation.components.ErrorOccurred
import com.example.pexelsapp.presentation.components.PhotosLazyList
import com.example.pexelsapp.presentation.components.SearchResultEmpty

@Composable
fun BookmarksPhotosLazyColumn(
    uiStateValue: UiState<List<Photo>>,
    onPhotoClick: (Photo) -> Unit,
    onExploreClick: () -> Unit,
    uploadNextPage: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 31.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiStateValue !is UiState.Failure) {
            PhotosLazyList(
                photos = uiStateValue.data ?: emptyList(),
                showAuthorName = true,
                isLoading = uiStateValue is UiState.Loading,
                onPhotoClick = onPhotoClick,
                uploadNextPage = uploadNextPage
            )
        } else {
            when (uiStateValue.errorType) {
                ErrorType.EmptyResult ->
                    BookmarksEmpty(onExploreClick)

                else -> Box {}
            }
        }
    }
}