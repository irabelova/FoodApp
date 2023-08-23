package com.example.food.data.network.dto

import com.squareup.moshi.Json

data class FoodDto(
    val id: Long,
    val name: String,
    @Json(name = "thumbnail_url")
    val thumbnailUrl: String,
    val description: String,
    @Json(name = "total_time_minutes")
    val price: Int?,
)

data class FoodResultDto(
    val results: List<FoodDto>
)
