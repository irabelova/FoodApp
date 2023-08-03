package com.example.food.presentation.mainScreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.food.R

@Composable
fun BannerElement(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .width(300.dp)
            .height(112.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}

@Composable
fun Banners(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .horizontalScroll(rememberScrollState())
        .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        bannersList.forEach { item ->
            BannerElement(item)
        }
    }
}

private val bannersList = listOf (
    R.drawable.ic_banner1,
    R.drawable.ic_banner2,
    R.drawable.ic_banner3
)

@Preview(showBackground = true)
@Composable
fun BannersPreview() {
    Banners(
        modifier = Modifier
    )
}