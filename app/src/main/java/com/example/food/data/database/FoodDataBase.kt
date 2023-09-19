package com.example.food.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.food.data.database.entities.CartItemEntity
import com.example.food.data.database.entities.CategoryEntity
import com.example.food.data.database.entities.FoodEntity
import com.example.food.data.database.entities.PromoCodeEntity

@Database(entities = [CategoryEntity::class, FoodEntity::class, CartItemEntity::class, PromoCodeEntity::class], version = 1)

abstract class FoodDataBase: RoomDatabase() {
    abstract fun foodDao(): FoodDao

    abstract fun cartItemDao(): CartItemDao

    abstract fun promoCodeDao(): PromoCodeDao
}