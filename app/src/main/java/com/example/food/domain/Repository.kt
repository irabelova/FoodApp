package com.example.food.domain

import android.util.Log
import com.example.food.domain.models.CartItem
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import com.example.food.domain.models.FoodCartItem
import com.example.food.domain.models.PromoCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataSource: DataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getCategories(): List<Category>  {
        return try {
            val result = dataSource.getCategories()
            saveCategories(result)
            result
        } catch (ex: Exception) {
            Log.e("Repository", "Error while loading categories", ex)
            localDataSource.getCategories()
        }
    }

    suspend fun getFoods(category: Category): List<Food> {
        return try {
            val result = dataSource.getFoodsByCategory(category)
            saveFoods(result, category.id)
            result
        } catch (ex: Exception) {
            Log.e("Repository", "Error while loading foods", ex)
            localDataSource.getFoodsByCategory(category)
        }
    }

    suspend fun getFoodItem(id: Long): Food {
        return try {
            val foodItem = localDataSource.getFoodItem(id)
            foodItem
        } catch (ex: Exception) {
            Log.e("Repository", "Error while loading foods", ex)
            dataSource.getFoodItem(id)
        }
    }


     fun getFoodCartItems(): Flow<List<FoodCartItem>> {
            return localDataSource.getCartItems().map {
                it.map {cartItem ->
                    val food = getFoodItem(cartItem.foodId)
                    FoodCartItem(cartItem, food)
                }
            }
    }

    suspend fun updateCartItem(foodId: Long, quantity: Int) {
        localDataSource.updateCartItem(foodId, quantity)
    }

    suspend fun saveCartItem (cartItem: CartItem) {
        localDataSource.saveItemToCart(cartItem)
    }

    suspend fun deleteCartItem(cartItem: CartItem) {
        localDataSource.deleteCartItem(cartItem)
    }

    suspend fun getPromoCode (couponName: String): PromoCode? {
        return try {
            val promoCode = localDataSource.getPromoCode(couponName)
            promoCode
        } catch (ex: Exception) {
            Log.e("Repository", "Error while loading promoCode", ex)
            null
        }
    }

    fun getPromoCodeList (): Flow<List<PromoCode>> {
        return localDataSource.getPromoCodeList()
    }

    suspend fun insertPromoCode (promoCode: PromoCode)  {
        try {
            localDataSource.insertPromoCode(promoCode)
        } catch (ex: Exception) {
            Log.e("Repository", "error while saving promo code", ex)
        }
    }

    suspend fun deletePromoCode (promoCode: PromoCode)  {
        try {
            localDataSource.deletePromoCode(promoCode)
        } catch (ex: Exception) {
            Log.e("Repository", "error while deleting promo code", ex)
        }
    }

    private suspend fun saveCategories(categories: List<Category>)  {
        try {
            localDataSource.saveCategories(categories)
        } catch (ex: Exception) {
            Log.e("Repository", "error while saving categories", ex)
        }
    }

    private suspend fun saveFoods(food: List<Food>, categoryId: Long) {
        try {
            localDataSource.saveFoodList(food, categoryId)
        } catch (ex: Exception) {
            Log.e("Repository", "error while saving foods", ex)
        }
    }
}