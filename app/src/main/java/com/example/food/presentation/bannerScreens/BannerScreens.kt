package com.example.food.presentation.bannerScreens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.food.presentation.FoodBottomMenuItem
import com.example.food.R
import com.example.food.presentation.commonComposables.BannerLottieAnimation
import com.example.food.presentation.commonComposables.getBannerList

@Composable
fun BannerItem(
    currentPage: Int,
    listOfScreens: List<@Composable () -> Unit>
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        content = listOfScreens[currentPage]
    )
}

@Composable
fun BannerScreen(
    navController: NavController,
    steps: Int,
    index: Int,
    bannerViewModel: BannerViewModel
) {
    val bannersList = getBannerList{bannerViewModel.insertPromoCode()}

    val isPressed = bannerViewModel.isPressed.observeAsState().value
    val currentStep = bannerViewModel.currentStep.observeAsState().value

    val goToNextScreen: () -> Unit = {
        if (index + 1 < bannersList.size) {
            navController.navigate("banners/$steps/${index + 1}")
            bannerViewModel.setCurrentStep(true)
        } else {
            navController.popBackStack(FoodBottomMenuItem.Menu.route, false)
        }
    }
    val goToPreviousScreen = {
        if (index > 0 && index != currentStep) {
            navController.navigate("banners/$steps/${index - 1}")
        } else if (index > 0) {
            navController.popBackStack()
        }
        bannerViewModel.setCurrentStep(false)
    }

    BackHandler(
        enabled = true
    ) {
        navController.popBackStack(FoodBottomMenuItem.Menu.route, false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                val maxWidth = this.size.width
                detectTapGestures(
                    onPress = {
                        val pressStartTime = System.currentTimeMillis()
                        bannerViewModel.setIsPressedFlag(true)
                        this.tryAwaitRelease()
                        val pressEndTime = System.currentTimeMillis()
                        val totalPressTime = pressEndTime - pressStartTime
                        if (totalPressTime < 200) {
                            val isTapOnRightThreeQuarters = (it.x > (maxWidth / 4))
                            if (isTapOnRightThreeQuarters) {
                                goToNextScreen()
                            } else {
                                goToPreviousScreen()
                            }
                        }
                        bannerViewModel.setIsPressedFlag(false)
                    },
                )
            }
    ) {
        BannerItem(
            currentPage = index,
            listOfScreens = bannersList
        )
        BannerProgressBar(steps, index, isPressed!!, goToNextScreen)
    }
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
fun SpecialOfferBanner(
    onPromoCodeClicked: () -> Unit
) {
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
                    onPromoCodeClicked()
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

@Preview(showBackground = true)
@Composable
fun SpecialOfferBannerPreview() {
    SpecialOfferBanner(
        onPromoCodeClicked = {}
    )
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
