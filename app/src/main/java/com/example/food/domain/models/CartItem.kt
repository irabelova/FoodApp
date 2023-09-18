package com.example.food.domain.models

data class CartItem (
    val id: Long,
    val foodId: Long,
    val quantity: Int,
    val isAddedToCart: Boolean = false
)