package com.example.food.presentation.mainScreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.food.R
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


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
fun BannerLottieAnimation(
    modifier: Modifier,
    animationId: Int
) {
    val compositionResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            animationId
        )
    )

    val progress by animateLottieCompositionAsState(
        composition = compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0F
    )

    LottieAnimation(
        modifier = modifier,
        composition = compositionResult.value,
        progress = progress
    )
}

@Composable
fun WhyChooseUsBannerTexts(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(modifier) {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.Start),
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.Start),
            text = description,
            fontSize = 18.sp,
            color = colorResource(id = R.color.descriptionText),
        )
    }
}

@Composable
fun SpecialOfferBanner() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val dy by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    val travelDistance = with(LocalDensity.current) { 30.dp.toPx() }
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        colorResource(id = R.color.outlineColor),
                        colorResource(id = R.color.descriptionText),
                        colorResource(id = R.color.selectedCategoryBackground),
                    ),
                    start = Offset.Zero, end = Offset.Infinite
                ),
                alpha = 0.7f
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp),
            text = stringResource(id = R.string.special_offer),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.ten_percent_off_with_code),
            fontSize = 24.sp,
        )
        Surface(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    Toast
                        .makeText(context, R.string.promo_code_has_been_applied, Toast.LENGTH_SHORT)
                        .show()
                },
            border = BorderStroke(
                2.dp, color = colorResource(id = R.color.outlineColor)
            ),
            shape = RoundedCornerShape(8.dp),
            color = Color.LightGray,
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(8.dp),
                text = stringResource(id = R.string.save_ten),
                color = colorResource(id = R.color.outlineColor),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Icon(
            imageVector = Icons.Filled.ArrowUpward,
            contentDescription = "",
            tint = colorResource(id = R.color.outlineColor),
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
                .graphicsLayer {
                    translationY = dy * travelDistance
                },
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp),
            text = stringResource(id = R.string.apply),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.outlineColor)
        )
    }
}

@Composable
fun FreeDeliveryBanner() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.7F,
            painter = painterResource(id = R.drawable.banner_background),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Row {
                BannerLottieAnimation(
                    modifier = Modifier
                        .size(112.dp)
                        .align(Alignment.CenterVertically),
                    animationId = R.raw.delivery_animation
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterVertically),
                    text = stringResource(id = R.string.free_delivery),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.for_orders_over_fifty_dollars),
                fontSize = 24.sp
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.promo_code_is_not_required),
                fontSize = 24.sp
            )
        }
    }
}


@Composable
fun WhyChooseUsBanner() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp),
            color = colorResource(id = R.color.selectedCategory)
        ) {
            Box {
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.Center),
                    text = stringResource(id = R.string.why_choose_us),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                BannerLottieAnimation(
                    modifier = Modifier
                        .size(130.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    animationId = R.raw.premium_quality
                )
                WhyChooseUsBannerTexts(
                    title = stringResource(id = R.string.premium_quality_title),
                    description = stringResource(id = R.string.premium_quality_description)
                )
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                WhyChooseUsBannerTexts(
                    modifier = Modifier.weight(1f),
                    title = stringResource(id = R.string.competitive_price_title),
                    description = stringResource(id = R.string.competitive_price_description)
                )
                BannerLottieAnimation(
                    modifier = Modifier
                        .size(130.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    animationId = R.raw.competitive_price
                )
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                BannerLottieAnimation(
                    modifier = Modifier
                        .size(130.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    animationId = R.raw.broad_range
                )
                WhyChooseUsBannerTexts(
                    title = stringResource(id = R.string.broad_range_title),
                    description = stringResource(id = R.string.broad_range_description)
                )
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                WhyChooseUsBannerTexts(
                    modifier = Modifier.weight(1f),
                    title = stringResource(id = R.string.packaging_and_delivery_flexibility_title),
                    description = stringResource(id = R.string.packaging_and_delivery_flexibility_description)
                )
                BannerLottieAnimation(
                    modifier = Modifier
                        .size(130.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    animationId = R.raw.packaging_and_delivery
                )
            }
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.thank_you_for_choosing_us),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
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
    Banners(modifier = Modifier, bannersList = emptyList(), onBannerClicked = {})
}

@Preview(showBackground = true)
@Composable
fun SpecialOfferBannerPreview() {
    SpecialOfferBanner()
}

@Preview(showBackground = true)
@Composable
fun FreeDeliveryBannerPreview() {
    FreeDeliveryBanner()
}

@Preview(showBackground = true)
@Composable
fun WhyChooseUsBannerPreview() {
    WhyChooseUsBanner()
}