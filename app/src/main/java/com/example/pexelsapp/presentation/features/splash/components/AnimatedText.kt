package com.example.pexelsapp.presentation.features.splash.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelsapp.presentation.theme.White
import com.example.pexelsapp.R as Res

@Composable
fun BoxScope.AnimatedText() {

    Text(
        modifier = Modifier
            .padding(end = 45.dp, bottom = 50.dp)
            .align(Alignment.BottomEnd),
        text = stringResource(id = Res.string.splash_text),
        style = TextStyle(
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.mulishsemibold)),
            fontWeight = FontWeight(900),
            color = White
        )
    )
}