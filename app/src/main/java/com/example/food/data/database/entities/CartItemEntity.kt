package com.example.food.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartItems")
data class CartItemEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val foodId: Long,
    val quantity: Int
)
