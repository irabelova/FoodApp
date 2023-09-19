package com.example.food.domain

import com.example.food.data.network.dto.CategoryDto
import com.example.food.data.network.dto.FoodDto
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import javax.inject.Inject

class NetworkFoodMapper @Inject constructor(){

    fun categoryDtoToCategory(dto: CategoryDto) =
        Category(
            id = dto.id,
            name = dto.name,
            displayName = dto.displayName
        )

    fun foodDtoToFood(dto: FoodDto) =
        Food(
            id = dto.id,
            name = dto.name,
            thumbnailUrl = dto.thumbnailUrl,
            description = dto.description,
            price = (dto.price ?: 0).toFloat()
        )
}