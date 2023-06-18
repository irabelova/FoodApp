package com.example.food.domain

import android.util.Log
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food

class Repository(
    private val dataSource: DataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getCategories(): List<Category> {
        return try {
            val result = dataSource.getCategories()
            saveCategories(result)
            result
        } catch (ex: Exception) {
            Log.e("Repository", "Error while loading categories", ex)
            localDataSource.getCategories()
        }
    }

    suspend fun getFoods(category: Category): List<Food> {
        return try {
            val result = dataSource.getFoodsByCategory(category)
            saveFoods(result, category)
            result
        } catch (ex: Exception) {
            Log.e("Repository", "Error while loading foods", ex)
            localDataSource.getFoodsByCategory(category)
        }
    }

    private suspend fun saveCategories(categories: List<Category>) {
        try {
            localDataSource.saveCategories(categories)
        } catch (ex: Exception) {
            Log.e("Repository", "error whole saving categories", ex)
        }
    }

    private suspend fun saveFoods(food: List<Food>, category: Category) {
        try {
            localDataSource.saveFoodList(food, category.id)
        } catch (ex: Exception) {
            Log.e("Repository", "error whole saving foods", ex)
        }
    }
}