package com.example.food.presentation.foodItemScreen

import com.example.food.domain.models.CartItem
import com.example.food.domain.models.Food

sealed interface FoodItemUiModel {
    object Loading: FoodItemUiModel
    data class Data(val foodItem: Food, val cartItem: CartItem): FoodItemUiModel
    object Error: FoodItemUiModel
}