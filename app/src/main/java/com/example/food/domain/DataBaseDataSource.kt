package com.example.food.domain

import com.example.food.data.database.FoodDao
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food

class DataBaseDataSource(
    private val foodDao: FoodDao,
    private val mapper: DataBaseFoodMapper
): LocalDataSource {
    override suspend fun saveCategories(categories: List<Category>) {
        foodDao.insertCategories(categories.map { mapper.categoryToCategoryEntity(it) })
    }

    override suspend fun saveFoodList(food: List<Food>, categoryId: Long) {
        foodDao.insertFood(food.map { mapper.foodToFoodEntity(it, categoryId) })
    }

    override suspend fun getCategories(): List<Category> {
        return foodDao.getCategories().map { mapper.categoryEntityToCategory(it) }
    }

    override suspend fun getFoodsByCategory(category: Category): List<Food> {
        return foodDao.getFoodByCategoryId(category.id).map { mapper.foodEntityToFood(it) }
    }
}