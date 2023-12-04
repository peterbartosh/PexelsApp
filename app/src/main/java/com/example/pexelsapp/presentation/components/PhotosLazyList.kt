package com.example.pexelsapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.data.utils.PHOTOS_PER_PAGE
import com.example.pexelsapp.data.utils.getWidthPercent
import com.example.pexelsapp.domain.Photo


@Composable
fun PhotosLazyList(
    photos: List<Photo>,
    showAuthorName: Boolean,
    isLoading: Boolean,
    uploadNextPage: () -> Unit,
    onPhotoClick: (Photo) -> Unit
) {

    val wp = LocalContext.current.getWidthPercent()

    val scrollState = rememberLazyStaggeredGridState()

    LaunchedEffect(key1 = isLoading){
        if (isLoading)
            scrollState.animateScrollToItem(0)
    }

    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        state = scrollState,
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center
    ) {
        items(
            items = photos,
            key = { item -> item.url }
        ) { photo ->
            val k = if (isLoading) 1f else photo.height.toFloat()/photo.width
            val minHeight = wp * (40 * k)
            Box(modifier = Modifier.width(minHeight).wrapContentHeight()) {
                PhotoCard(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .bounceClickEffect(),
                    photo = photo,
                    minImageHeight = minHeight,
                    showAuthorName = showAuthorName,
                    isLoadingData = isLoading,
                    onPhotoClick = onPhotoClick
                )
            }
        }
        if (photos.size >= PHOTOS_PER_PAGE && photos.size % PHOTOS_PER_PAGE == 0)
            item {
                LaunchedEffect(key1 = true) {
                    uploadNextPage()
                }
                CircleLoadingBox(isLoading = true)
            }
    }
}