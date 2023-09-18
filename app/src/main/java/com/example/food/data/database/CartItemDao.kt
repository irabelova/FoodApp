package com.example.food.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.food.data.database.entities.FOOD_TABLE_NAME
import com.example.food.data.database.entities.FoodEntity


//@Dao
//interface CartItemDao {
//    @Insert
//    suspend fun insertCartItem(foodEntity: FoodEntity)
//
//    @Update
//    suspend fun updateCartItem(foodEntity: FoodEntity)
//
//
//    @Query("SELECT * FROM CartItems")
//    fun getCartItems(): List<FoodEntity>
//
//    @Query("SELECT SUM(quantity) FROM CartItems")
//    fun getQuantityOfItems(): Int
//
//    @Query("SELECT * FROM CartItems WHERE id = :id")
//    fun getFoodItem(id: Long): FoodEntity
//}