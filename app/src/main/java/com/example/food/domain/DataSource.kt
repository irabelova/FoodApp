package com.example.food.domain

import com.example.food.domain.models.Category
import com.example.food.domain.models.Food

interface DataSource {
    suspend fun getCategories(): List<Category>
    suspend fun getFoodsByCategory(category: Category): List<Food>

    suspend fun getFoodItem(id: Long, categoryId: Long): Food
}

interface LocalDataSource: DataSource {
    suspend fun saveCategories(categories: List<Category>)
    suspend fun saveFoodList(food: List<Food>)

    suspend fun updateCartItem(foodItem: Food)

    suspend fun getCartItems(): List<Food>

}