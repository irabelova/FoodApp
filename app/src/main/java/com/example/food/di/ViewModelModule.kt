package com.example.food.di

import androidx.lifecycle.ViewModel
import com.example.food.presentation.bannerScreens.BannerViewModel
import com.example.food.presentation.checkoutScreen.CheckoutViewModel
import com.example.food.presentation.mainScreen.FoodViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FoodViewModel::class)
    abstract fun bindFoodViewModel(viewModel: FoodViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CheckoutViewModel::class)
    abstract fun bindCheckoutViewModel(viewModel: CheckoutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BannerViewModel::class)
    abstract fun bindBannerViewModel(viewModel: BannerViewModel): ViewModel
}