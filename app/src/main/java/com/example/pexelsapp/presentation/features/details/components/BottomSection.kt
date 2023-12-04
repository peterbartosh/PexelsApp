package com.example.pexelsapp.presentation.features.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelsapp.R
import com.example.pexelsapp.presentation.components.CircleLoadingBox
import com.example.pexelsapp.presentation.theme.Red
import com.example.pexelsapp.presentation.theme.White

@Composable
fun BoxScope.BottomSection(
    isDownloading: Boolean,
    isInBookmarks: Boolean,
    onDownloadClick: () -> Unit,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp)
            .padding(bottom = 25.dp)
            .align(Alignment.BottomCenter),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .width(180.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ){
            IconButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(Red)
                    .align(Alignment.CenterStart),
                onClick = onDownloadClick
            ) {
                if (isDownloading)
                    CircleLoadingBox(isLoading = true)
                else
                    Icon(
                        modifier = Modifier
                            .width(13.dp)
                            .height(13.dp),
                        painter = painterResource(id = R.drawable.download_icon),
                        contentDescription = null,
                        tint = White
                    )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 37.dp),
                text = stringResource(id = R.string.download),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.mulishromanmedium)),
                    fontWeight = FontWeight(600),
                    textAlign = TextAlign.Center,
                )
            )

        }

        IconButton(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            onClick = if (isInBookmarks) onRemove else onAdd
        ) {
            Icon(
                modifier = Modifier
                    .width(18.dp)
                    .height(20.dp),
                painter = painterResource(id = if (isInBookmarks)
                    R.drawable.bookmarks_selected_icon
                else
                    R.drawable.bookmarks_unselected_icon
                ),
                contentDescription = null,
                tint = if (!isInBookmarks) MaterialTheme.colorScheme.onBackground else Red
            )
        }

    }
}