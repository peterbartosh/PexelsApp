package com.example.pexelsapp.presentation.features.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelsapp.R
import com.example.pexelsapp.data.model.remote.primary.NetworkCollection
import com.example.pexelsapp.data.utils.FEATURED_COLLECTIONS_PER_PAGE
import com.example.pexelsapp.domain.Collection
import com.example.pexelsapp.presentation.components.CircleLoadingBox
import com.example.pexelsapp.presentation.components.shimmerEffect
import com.example.pexelsapp.presentation.theme.Red
import com.example.pexelsapp.presentation.theme.White
import kotlinx.coroutines.launch

@Composable
fun FeaturedCollectionsRow(
    scrollState: LazyListState,
    collectionPicked: Boolean,
    collections: List<Collection>,
    uploadNextPage: () -> Unit,
    onClick: (Collection) -> Unit
) {

    if (collections.isNotEmpty())
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {

            LazyRow(
                state = scrollState,
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(
                    items = collections,
                    key = { _, item -> item.hashCode() }
                ) { index, collection ->
                    CollectionButton(
                        collection = collection,
                        isSelected = collectionPicked && index == 0,
                        onClick = { onClick(collection) }
                    )
                }
                item {
                    LaunchedEffect(key1 = true) {
                        uploadNextPage()
                    }
                    CircleLoadingBox(isLoading = true)
                }
            }
        }
}
