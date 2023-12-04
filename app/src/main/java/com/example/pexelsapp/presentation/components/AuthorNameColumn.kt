package com.example.pexelsapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelsapp.R
import com.example.pexelsapp.presentation.theme.Black
import com.example.pexelsapp.presentation.theme.White


@Composable
fun BoxScope.AuthorNameColumn(authorName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Black.copy(alpha = 0.3f))
            .height(33.dp)
            .align(Alignment.BottomCenter),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = authorName,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.mulishromanmedium)),
                fontWeight = FontWeight(400),
                color = White,
                textAlign = TextAlign.Center,
            )
        )
    }
}