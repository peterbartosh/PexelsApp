package com.example.pexelsapp.presentation.features.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pexelsapp.R
import com.example.pexelsapp.data.utils.UiState
import com.example.pexelsapp.domain.Photo
import com.example.pexelsapp.presentation.components.LinearLoadingRow
import com.example.pexelsapp.presentation.features.bookmarks.components.BookmarksPhotosLazyColumn
import com.example.pexelsapp.presentation.features.details.DetailsScreen
import com.example.pexelsapp.presentation.features.details.DetailsViewModel

@Composable
fun BookmarksScreen(
    bookmarksViewModel: BookmarksViewModel,
    onPhotoClick: (Photo) -> Unit,
    onExploreClick: () -> Unit
) {

    val uiState by bookmarksViewModel.uiState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.bookmarks),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.mulishromanbold)),
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Center,
                )
            )
        }

        LinearLoadingRow(isLoading = uiState is UiState.Loading)

        BookmarksPhotosLazyColumn(
            uiStateValue = uiState,
            onPhotoClick = onPhotoClick,
            onExploreClick = onExploreClick,
            uploadNextPage = {}
        )
    }
}