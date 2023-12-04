package com.example.pexelsapp.presentation.features.splash.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.R
import com.example.pexelsapp.data.utils.dpToPx
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

@Composable
fun AnimatedImage(onFinish: () -> Unit) {

    val animationDuration = 1200
    val jumpPx = 350

    val x = 45.dp.dpToPx().toInt()
    val y = 49.dp.dpToPx().toInt()

    val offY = remember {
        Animatable(y.toFloat())
    }

    val rotation = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        val job1 = launch {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = tween(durationMillis = animationDuration)
            )
        }
        val job2 = launch {
            offY.animateTo(
                targetValue = offY.value - jumpPx,
                animationSpec = tween(durationMillis = animationDuration / 2)
            )
            offY.animateTo(
                targetValue = offY.value + jumpPx,
                animationSpec = tween(durationMillis = animationDuration / 2)
            )
        }

        joinAll(job1, job2)
        delay(200)
        onFinish()
    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .offset { IntOffset(x, offY.value.toInt()) }
            .graphicsLayer {
                rotationY = rotation.value
                rotationX = rotation.value
                cameraDistance = 12f * density
            }
    ){
        Image(
            modifier = Modifier
                .width(120.dp)
                .height(141.dp),
            painter = painterResource(id = R.drawable.splash_icon_no_background),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
    }
}