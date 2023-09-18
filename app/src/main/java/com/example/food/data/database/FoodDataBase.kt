package com.example.food.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.food.data.database.entities.CategoryEntity
import com.example.food.data.database.entities.FoodEntity

//@Database(entities = [CategoryEntity::class, FoodEntity::class, CartItem::class], version = 1)

@Database(entities = [CategoryEntity::class, FoodEntity::class], version = 1)
abstract class FoodDataBase: RoomDatabase() {
    abstract fun foodDao(): FoodDao

//    abstract fun cartItemDao(): CartItemDao
}