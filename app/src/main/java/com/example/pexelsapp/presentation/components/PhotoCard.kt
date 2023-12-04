package com.example.pexelsapp.presentation.components

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pexelsapp.R
import com.example.pexelsapp.domain.Photo


@Composable
inline fun PhotoCard(
    modifier: Modifier = Modifier,
    photo: Photo?,
    minImageHeight: Dp,
    showAuthorName: Boolean,
    isLoadingData: Boolean,
    crossinline onError: () -> Unit = {},
    crossinline onSuccess: (Bitmap?) -> Unit = {},
    crossinline onPhotoClick: (Photo) -> Unit = {}
) {

    var isReady by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier)
            .clickable { if (isReady) onPhotoClick(photo!!) }
            .shimmerEffect(isLoading = !isReady),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .shimmerEffect(!isReady)
        ) {
            if (!isLoadingData) {
                val imgId = if (!isSystemInDarkTheme()) R.drawable.placeholder_light else R.drawable.placeholder
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = minImageHeight)
                        .wrapContentHeight()
                        .align(Alignment.Center),
                    model = photo?.url,
                    placeholder = painterResource(id = imgId),
                    onError = {
                        onError()
                    },
                    onSuccess = {
                        isReady = true
                        onSuccess((it.result.drawable as BitmapDrawable?)?.bitmap)
                    },
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )
                if (showAuthorName)
                    AuthorNameColumn(authorName = photo!!.authorName)
            } else
                Placeholder(minImageHeight)
        }
    }
}