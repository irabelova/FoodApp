package com.example.food

import com.example.food.data.database.entities.CategoryEntity
import com.example.food.data.database.entities.FoodEntity
import com.example.food.data.network.dto.CategoryDto
import com.example.food.data.network.dto.FoodDto
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food

val categoryDto = CategoryDto(
    id = 1,
    type = "cuisine",
    name = "italian",
    displayName = "Italian"
)

val categoryModel = Category(
    id = 1,
    name = "italian",
    displayName = "Italian"
)

val categoryEntity = CategoryEntity(
    id = 1,
    name = "italian",
    displayName = "Italian"
)

val foodDto = FoodDto(
    id = 11,
    name = "Soup",
    thumbnailUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4ehfDVe_Y5YuvJ7oc14SWbndJyWn5Ya49cQ&usqp=CAU",
    description = "SoupSoup",
    timeMinutes = 45
)

val foodModel = Food(
    id = 11,
    name = "Soup",
    thumbnailUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4ehfDVe_Y5YuvJ7oc14SWbndJyWn5Ya49cQ&usqp=CAU",
    description = "SoupSoup",
    timeMinutes = 45
)

val foodEntity = FoodEntity(
    id = 11,
    name = "Soup",
    thumbnailUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4ehfDVe_Y5YuvJ7oc14SWbndJyWn5Ya49cQ&usqp=CAU",
    description = "SoupSoup",
    timeMinutes = 45,
    categoryId = 1
)