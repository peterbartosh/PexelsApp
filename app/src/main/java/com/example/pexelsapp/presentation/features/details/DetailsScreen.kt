package com.example.pexelsapp.presentation.features.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pexelsapp.data.utils.getWidthPercent
import com.example.pexelsapp.data.utils.pxToDp
import com.example.pexelsapp.presentation.features.details.components.BottomSection
import com.example.pexelsapp.presentation.features.details.components.ImageSection
import com.example.pexelsapp.presentation.features.details.components.UpperSection
import com.example.pexelsapp.presentation.features.home.HomeScreen
import com.example.pexelsapp.presentation.features.home.HomeViewModel

@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel,
    photoId: Int?,
    isNetworkRequest: Boolean,
    onBackPressed: () -> Unit,
    onExploreClick: () -> Unit
) {

    val context = LocalContext.current

    val wp = context.getWidthPercent()

    val uiState = detailsViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        detailsViewModel.getPhoto(photoId, isNetworkRequest)
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 25.dp)
                .align(Alignment.TopCenter)
        ) {

            UpperSection(uiState.value, onBackPressed)

            ImageSection(
                uiStateValue = uiState.value,
                minHeight = wp * 40,
                onExploreClick = onExploreClick,
                onError = { detailsViewModel.notifyState(isSuccess = false) },
                onSuccess = { bmp -> detailsViewModel.notifyState(isSuccess = true, bmp = bmp) }
            )
        }

        BottomSection(
            isDownloading = detailsViewModel.downloadingState,
            isInBookmarks = detailsViewModel.isInBookmarks,
            onDownloadClick = { detailsViewModel.savePhotoToGallery(context) },
            onAdd = detailsViewModel::addPhotoToBookmarks,
            onRemove = detailsViewModel::removePhotoFromBookmarks,
        )

    }
}