package com.example.food.domain

import android.util.Log
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataSource: DataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getCategories(): List<Category>  {
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
            saveFoods(result)
            result
        } catch (ex: Exception) {
            Log.e("Repository", "Error while loading foods", ex)
            localDataSource.getFoodsByCategory(category)
        }
    }

    suspend fun getFoodItem(id: Long, categoryId: Long): Food {
        return try {
            val foodItem = localDataSource.getFoodItem(id, categoryId)
            foodItem
        } catch (ex: Exception) {
            Log.e("Repository", "Error while loading foods", ex)
            dataSource.getFoodItem(id, categoryId)
        }
    }

    suspend fun updateCartItem(foodItem: Food) {
        try {
            localDataSource.updateCartItem(foodItem)
        } catch (ex: Exception) {
            Log.e("Repository", "error while updating cart item", ex)
        }
    }


    suspend fun getCartItems(): List<Food>  {
        try {
            return localDataSource.getCartItems()
        } catch (ex: Exception) {
            Log.e("Repository", "error while getting cart items", ex)
            throw IllegalArgumentException()
        }
    }


    private suspend fun saveCategories(categories: List<Category>)  {
        try {
            localDataSource.saveCategories(categories)
        } catch (ex: Exception) {
            Log.e("Repository", "error while saving categories", ex)
        }
    }

    private suspend fun saveFoods(food: List<Food>) {
        try {
            localDataSource.saveFoodList(food)
        } catch (ex: Exception) {
            Log.e("Repository", "error while saving foods", ex)
        }
    }
}