package com.example.food.presentation

import com.example.food.domain.models.Category
import com.example.food.domain.models.Food

sealed interface CategoryState {
    object InitialLoading: CategoryState
    data class CategoryData(
        val categories: List<Category>,
    ): CategoryState
    object InitialLoadingError: CategoryState
}

sealed interface FoodState {
    object Loading: FoodState
    data class FoodData(val foodList: List<Food>): FoodState
    object Error: FoodState
}