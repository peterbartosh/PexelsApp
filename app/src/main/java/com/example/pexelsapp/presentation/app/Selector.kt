package com.example.pexelsapp.presentation.app

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.data.utils.SCREEN_TRANSITION_DURATION_MS
import com.example.pexelsapp.data.utils.getWidthPercent
import com.example.pexelsapp.presentation.theme.Red
import java.nio.channels.Selector

@Composable
fun Selector(
    selectedIndex: Int,
    itemsAmount: Int = 2,
    selectorHeight: Dp = 2.dp,
    selectorWidth: Dp = 24.dp
) {

    val wp = LocalContext.current.getWidthPercent()

    val rowWidth = wp * (100 * (itemsAmount.toFloat() - 1) / itemsAmount) + selectorWidth
    val targetValue = selectedIndex * (1f / (itemsAmount - 1))

    val offsetX = remember {
        Animatable(targetValue)
    }

    LaunchedEffect(key1 = selectedIndex){
        offsetX.animateTo(
            targetValue = targetValue,
            animationSpec = tween(SCREEN_TRANSITION_DURATION_MS)
        )
    }

    Row(
        modifier = Modifier
            .width(rowWidth)
            .height(selectorHeight)
    ) {

        Column(
            modifier = Modifier
                .offset(x = if (selectedIndex == itemsAmount - 1)
                    rowWidth * offsetX.value - selectorWidth
                else
                    rowWidth * offsetX.value
                )
                .width(selectorWidth)
                .fillMaxHeight()
                .background(Red, RoundedCornerShape(10.dp))

        ) {}

    }
}