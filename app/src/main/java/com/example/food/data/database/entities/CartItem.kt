package com.example.food.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartItems")
data class CartItem (
    @PrimaryKey
    val id: Long,
    val imageUrl: String,
    val title: String,
    val price: Int,
    val quantity: Int,
    val isAddedToCart: Boolean = false
)