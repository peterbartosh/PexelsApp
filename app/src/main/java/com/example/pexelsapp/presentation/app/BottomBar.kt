package com.example.pexelsapp.presentation.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.R
import com.example.pexelsapp.data.utils.NAVIGATION_BAR_HEIGHT
import com.example.pexelsapp.presentation.features.bookmarks.bookmarksRoute
import com.example.pexelsapp.presentation.features.home.homeRoute

data class MainItem(
    val selectedIconInd: Int,
    val unselectedIconInd: Int,
    val route : String
)

@Preview(showBackground = true)
@Composable
fun BottomBar(
    selectedIndex: Int = 0,
    navigate: (String) -> Unit = {},
) {

    val items = listOf(
        MainItem(
            selectedIconInd = R.drawable.home_selected_icon,
            unselectedIconInd = R.drawable.home_unselected_icon,
            route = homeRoute
        ),
        MainItem(
            selectedIconInd = R.drawable.bookmarks_selected_icon,
            unselectedIconInd = R.drawable.bookmarks_unselected_icon,
            route = bookmarksRoute
        )
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Selector(selectedIndex)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(NAVIGATION_BAR_HEIGHT.dp)
        ) {
            items.forEachIndexed { index, item ->

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable {
                            navigate(item.route)
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(
                            if (selectedIndex == index)
                                item.selectedIconInd
                            else
                                item.unselectedIconInd
                        ),
                        contentDescription = null
                    )
                }
            }
        }
    }
}