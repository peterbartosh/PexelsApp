package com.example.pexelsapp.presentation.components


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.pexelsapp.presentation.theme.Black
import com.example.pexelsapp.presentation.theme.White

fun Modifier.shimmerEffect(
    isLoading: Boolean = true,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 700,
) = if (!isLoading)
        this
    else {
    composed {

        val shimmerColors = getColours()

        val transition = rememberInfiniteTransition()

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            )
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
        )
    }
}


@Composable
private fun getColours(): List<Color> {
    val color = MaterialTheme.colorScheme.onBackground
    return listOf(
        color.copy(alpha = 0.2f),
        color.copy(alpha = 0.3f),
        color.copy(alpha = 0.4f),
        color.copy(alpha = 0.3f),
        color.copy(alpha = 0.2f),
    )
}