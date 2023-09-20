package com.example.food.presentation.mainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun BannerElement(
    modifier: Modifier = Modifier,
    item: @Composable () -> Unit,
    index: Int,
    onBannerClicked: (Int) -> Unit
) {
    Surface(
        modifier = modifier
            .width(300.dp)
            .height(112.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onBannerClicked(index)
            },
        content = item
    )
}

@Composable
fun Banners(
    bannersList: List<@Composable () -> Unit>,
    onBannerClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        bannersList.forEach {
            BannerElement(
                item = it,
                index = bannersList.indexOf(it),
                onBannerClicked = onBannerClicked,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BannersPreview() {
    Banners(modifier = Modifier, bannersList = emptyList(), onBannerClicked = {})
}