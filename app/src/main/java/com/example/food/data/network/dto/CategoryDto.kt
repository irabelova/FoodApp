package com.example.food.data.network.dto

import com.squareup.moshi.Json

data class CategoryDto(
    val id: Long,
    val type: String,
    val name: String,
    @Json(name = "display_name")
    val displayName: String
)

data class CategoriesDto(
    val results: List<CategoryDto>
)
