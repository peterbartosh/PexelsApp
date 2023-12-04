package com.example.pexelsapp.presentation.features.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelsapp.R
import com.example.pexelsapp.domain.Collection
import com.example.pexelsapp.presentation.theme.White


@Composable
fun CollectionButton(
    collection: Collection,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val containerColor = if (isSelected)
        MaterialTheme.colorScheme.tertiary
    else
        MaterialTheme.colorScheme.primaryContainer

    val contentColor = if (isSelected)
        White
    else
        MaterialTheme.colorScheme.onBackground

    Button(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(end = 20.dp),
        onClick = onClick,
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {

        Text(
            text = collection.title,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.mulishromanmedium)),
                fontWeight = FontWeight(700),
                letterSpacing = 0.28.sp
            )
        )

    }
}