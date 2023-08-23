package com.example.food.domain.models

data class Food(
    val id: Long,
    val name: String,
    val thumbnailUrl: String,
    val description: String,
    val price: Int,
)
