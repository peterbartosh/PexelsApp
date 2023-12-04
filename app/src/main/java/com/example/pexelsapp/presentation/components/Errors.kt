package com.example.pexelsapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelsapp.presentation.theme.Red
import com.example.pexelsapp.R as Res

@Composable
fun ErrorComp(
    imageId: Int? = null,
    upperTextId: Int = -1,
    lowerClickableTextId: Int,
    onLowerTextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageId != null){
            Image(
                modifier = Modifier
                    .height(102.dp)
                    .aspectRatio(128f / 102f),
                painter = painterResource(id = imageId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        } else {
            Text(text = stringResource(id = upperTextId))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.clickable { onLowerTextClick() },
            text = stringResource(id = lowerClickableTextId),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.mulishromanmedium)),
                fontWeight = FontWeight(700),
                color = MaterialTheme.colorScheme.tertiary
            )
        )
    }
}

@Composable
fun ErrorOccurred(onLowerTextClick: () -> Unit) {
    ErrorComp(
        imageId = Res.drawable.no_network_icon,
        lowerClickableTextId = Res.string.try_again,
        onLowerTextClick = onLowerTextClick
    )
}

@Composable
fun SearchResultEmpty(onLowerTextClick: () -> Unit) {
    ErrorComp(
        upperTextId = Res.string.search_result_empty,
        lowerClickableTextId = Res.string.explore,
        onLowerTextClick = onLowerTextClick
    )
}

@Composable
fun BookmarksEmpty(onLowerTextClick: () -> Unit) {
    ErrorComp(
        upperTextId = Res.string.bookmarks_empty,
        lowerClickableTextId = Res.string.explore,
        onLowerTextClick = onLowerTextClick
    )
}

@Composable
fun ImageNotFound(onLowerTextClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ErrorComp(
            upperTextId = Res.string.image_not_found,
            lowerClickableTextId = Res.string.explore,
            onLowerTextClick = onLowerTextClick
        )
    }
}