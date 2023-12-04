package com.example.pexelsapp.presentation.features.details.components

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.data.utils.UiState
import com.example.pexelsapp.data.utils.getWidthPercent
import com.example.pexelsapp.domain.Photo
import com.example.pexelsapp.presentation.components.ImageNotFound
import com.example.pexelsapp.presentation.components.PhotoCard

@Composable
fun ImageSection(
    uiStateValue: UiState<Photo>,
    minHeight: Dp,
    onExploreClick: () -> Unit,
    onError: () -> Unit,
    onSuccess: (Bitmap?) -> Unit,
) {


    var showStub by remember {
        mutableStateOf(false)
    }

    var scale by remember {
        mutableStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    BoxWithConstraints {

        val gesturesState = rememberTransformableState { zoomChange, panChange, rotationChange ->
            scale = (scale * zoomChange).coerceIn(1f, 5f)

            val extraWidth = (scale - 1) * constraints.maxWidth
            val extraHeight = (scale - 1) * constraints.maxHeight

            val maxX = extraWidth / 2
            val maxY = extraHeight / 2

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
            )
        }

        LaunchedEffect(key1 = gesturesState.isTransformInProgress) {
            if (!gesturesState.isTransformInProgress) {
                scale = 1f
                offset = Offset.Zero
            }
        }

        if (showStub)
            ImageNotFound(onExploreClick)
        else {
            LazyColumn(Modifier.padding(top = 38.dp)) {

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        PhotoCard(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                    translationX = offset.x
                                    translationY = offset.y
                                }
                                .transformable(gesturesState),
                            photo = uiStateValue.data,
                            minImageHeight = minHeight,
                            showAuthorName = false,
                            isLoadingData = uiStateValue is UiState.Loading,
                            onError = {
                                showStub = true
                                onError.invoke()
                            },
                            onSuccess = onSuccess
                        )
                    }
                }
            }
        }
    }
}
