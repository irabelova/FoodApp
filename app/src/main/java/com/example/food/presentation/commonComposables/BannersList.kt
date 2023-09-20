package com.example.food.presentation.commonComposables

import androidx.compose.runtime.Composable
import com.example.food.presentation.bannerScreens.FreeDeliveryBanner
import com.example.food.presentation.bannerScreens.SpecialOfferBanner
import com.example.food.presentation.bannerScreens.WhyChooseUsBanner

@Composable
fun getBannerList(onClickedBanner: () -> Unit = {}) =
    listOf<@Composable () -> Unit>(
    { FreeDeliveryBanner() },
    { WhyChooseUsBanner() },
    { SpecialOfferBanner(onClickedBanner) }
)