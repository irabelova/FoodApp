package com.example.food.domain

import com.example.food.domain.models.CartItem
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import com.example.food.domain.models.PromoCode
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getCategories(): List<Category>
    suspend fun getFoodsByCategory(category: Category): List<Food>

    suspend fun getFoodItem(id: Long): Food
}

interface LocalDataSource: DataSource {
    suspend fun saveCategories(categories: List<Category>)
    suspend fun saveFoodList(food: List<Food>, categoryId: Long)

    suspend fun saveItemToCart(cartItem: CartItem)

    suspend fun updateCartItem(foodId: Long, quantity: Int)

    suspend fun deleteCartItem (cartItem: CartItem)

    fun getCartItems(): Flow<List<CartItem>>

    suspend fun getPromoCode (couponName: String): PromoCode?

    suspend fun insertPromoCode (promoCode: PromoCode)

    suspend fun deletePromoCode (promoCode: PromoCode)

    fun getPromoCodeList (): Flow<List<PromoCode>>

}