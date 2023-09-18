package com.example.food.domain

import com.example.food.data.network.FoodApiService
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import javax.inject.Inject

class RapidApiDataSource @Inject constructor(
    private val service: FoodApiService,
    private val mapper: NetworkFoodMapper
): DataSource {
    override suspend fun getCategories(): List<Category> =
        service.getCategories().results.filter { it.type == CATEGORY_TYPE }.map { mapper.categoryDtoToCategory(it) }

    override suspend fun getFoodsByCategory(category: Category): List<Food> =
        service.getFoods(tag = category.name).results.map { mapper.foodDtoToFood(it) }

    override suspend fun getFoodItem(id: Long): Food {
        val foodItem = service.getFoodItem(id = id)
        return mapper.foodDtoToFood(foodItem)
    }

    private companion object {
        const val CATEGORY_TYPE = "cuisine"
    }

}