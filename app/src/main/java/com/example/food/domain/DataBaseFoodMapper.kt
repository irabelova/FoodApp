package com.example.food.domain

import com.example.food.data.database.entities.CartItemEntity
import com.example.food.data.database.entities.CategoryEntity
import com.example.food.data.database.entities.FoodEntity
import com.example.food.domain.models.CartItem
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import javax.inject.Inject

class DataBaseFoodMapper @Inject constructor(){

    fun foodToFoodEntity(model: Food, categoryId: Long) =
        FoodEntity(
            id = model.id,
            name = model.name,
            thumbnailUrl = model.thumbnailUrl,
            description = model.description,
            price = model.price,
            categoryId = categoryId,
        )

    fun categoryToCategoryEntity(model: Category) =
        CategoryEntity(
            id = model.id,
            name = model.name,
            displayName = model.displayName
        )

    fun foodEntityToFood(entity: FoodEntity) =
        Food(
            id = entity.id,
            name = entity.name,
            thumbnailUrl = entity.thumbnailUrl,
            description = entity.description,
            price = entity.price,
        )
    fun categoryEntityToCategory(entity: CategoryEntity) =
        Category(
            id = entity.id,
            name = entity.name,
            displayName = entity.displayName
        )

    fun cartItemEntityToCartItem(entity: CartItemEntity) =
        CartItem(
            id = entity.id,
            foodId = entity.foodId,
            quantity = entity.quantity,
            isAddedToCart = true
        )

    fun cartItemToCartItemEntity(model: CartItem) =
        CartItemEntity(
            id = model.id,
            foodId = model.foodId,
            quantity = model.quantity
        )
}