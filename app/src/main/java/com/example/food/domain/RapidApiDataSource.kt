package com.example.food.domain

import com.example.food.data.network.FoodApiService
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food

class RapidApiDataSource(
    private val service: FoodApiService,
    private val mapper: NetworkFoodMapper
): DataSource {
    override suspend fun getCategories(): List<Category> =
        service.getCategories().results.filter { it.type == CATEGORY_TYPE }.map { mapper.categoryDtoToCategory(it) }

    override suspend fun getFoodsByCategory(category: Category): List<Food> =
        service.getFoods(tag = category.name).results.map { mapper.foodDtoToFood(it) }

    private companion object {
        const val CATEGORY_TYPE = "cuisine"
    }

}