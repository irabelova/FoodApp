package com.example.food.presentation.foodItemScreen

import com.example.food.domain.models.Food

sealed interface FoodItemUiModel {
    object Loading: FoodItemUiModel
    data class Data(val foodItem: Food): FoodItemUiModel
    object Error: FoodItemUiModel
}