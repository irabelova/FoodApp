package com.example.food.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.food.data.database.entities.CATEGORY_TABLE_NAME
import com.example.food.data.database.entities.CategoryEntity
import com.example.food.data.database.entities.FOOD_TABLE_NAME
import com.example.food.data.database.entities.FoodEntity

@Dao
interface FoodDao {

    @Insert
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Insert
    suspend fun insertFood(food: List<FoodEntity>)

    @Query("SELECT * FROM $CATEGORY_TABLE_NAME")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT * FROM $FOOD_TABLE_NAME WHERE categoryId = :id")
    suspend fun getFoodByCategoryId(id: Long): List<FoodEntity>

    @Query("SELECT * FROM $FOOD_TABLE_NAME WHERE id = :id")
    fun getFoodItem(id: Long): FoodEntity
}