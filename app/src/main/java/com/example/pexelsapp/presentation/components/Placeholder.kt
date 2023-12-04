package com.example.pexelsapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.pexelsapp.R
import com.example.pexelsapp.data.utils.pxToDp

@Composable
fun BoxScope.Placeholder(minHeight: Dp) {
    val imgId = if (!isSystemInDarkTheme()) R.drawable.placeholder_light else R.drawable.placeholder
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .shimmerEffect(true)
            .align(Alignment.Center)
    ){
        Image(
            modifier = Modifier
                .size(50.pxToDp())
                .align(Alignment.Center),
            painter = painterResource(id = imgId),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
    }
}