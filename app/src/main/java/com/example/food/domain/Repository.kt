package com.example.food.domain

import android.util.Log
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import javax.inject.Inject

class Repository @Inject constructor(
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
            saveFoods(result, category.id)
            result
        } catch (ex: Exception) {
            Log.e("Repository", "Error while loading foods", ex)
            localDataSource.getFoodsByCategory(category)
        }
    }

    suspend fun getFoodItem(id: Long): Food {
        return try {
            val foodItem = dataSource.getFoodItem(id)
            foodItem
        } catch (ex: Exception) {
            Log.e("Repository", "Error while loading foods", ex)
            localDataSource.getFoodItem(id)
        }
    }

    private suspend fun saveCategories(categories: List<Category>) {
        try {
            localDataSource.saveCategories(categories)
        } catch (ex: Exception) {
            Log.e("Repository", "error whole saving categories", ex)
        }
    }

    private suspend fun saveFoods(food: List<Food>, categoryId: Long) {
        try {
            localDataSource.saveFoodList(food, categoryId)
        } catch (ex: Exception) {
            Log.e("Repository", "error whole saving foods", ex)
        }
    }
}