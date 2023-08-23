package com.example.food.presentation.mainScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.food.R


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
fun BannerElement1() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
//            .background(
//                Brush.linearGradient(
//                    colors = listOf(
//                        colorResource(id = R.color.purple_700),
//                        colorResource(id = R.color.teal_200),
//                        colorResource(id = R.color.teal_700),
//                    ),
//                    start = Offset.Zero, end = Offset.Infinite
//                )
//            )
        color = Color.Green
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Special offer",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "10% off",
                fontSize = 32.sp,
            )
            Surface(
                border = BorderStroke(
                    2.dp,
                    color = colorResource(id = R.color.outlineColor)
                ),
                shape = RoundedCornerShape(8.dp),
                color = Color.LightGray,
                modifier = Modifier
                    .width(140.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "SAVE10",
                        color = colorResource(id = R.color.outlineColor),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun BannerElement2() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.LightGray
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Special offer",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "10% off",
                fontSize = 32.sp,
            )
            Surface(
                border = BorderStroke(
                    2.dp,
                    color = colorResource(id = R.color.outlineColor)
                ),
                shape = RoundedCornerShape(8.dp),
                color = Color.LightGray,
                modifier = Modifier
                    .width(140.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "SAVE10",
                        color = colorResource(id = R.color.outlineColor),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun BannerElement3() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Red
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Special offer",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "10% off",
                fontSize = 32.sp,
            )
            Surface(
                border = BorderStroke(
                    2.dp,
                    color = colorResource(id = R.color.outlineColor)
                ),
                shape = RoundedCornerShape(8.dp),
                color = Color.LightGray,
                modifier = Modifier
                    .width(140.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "SAVE10",
                        color = colorResource(id = R.color.outlineColor),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
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
    Banners(
        modifier = Modifier,
        bannersList = emptyList(),
        onBannerClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
fun BannerElement1Preview() {
    BannerElement1()
}