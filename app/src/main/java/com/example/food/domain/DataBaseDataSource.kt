package com.example.food.domain

import com.example.food.data.database.CartItemDao
import com.example.food.data.database.FoodDao
import com.example.food.data.database.PromoCodeDao
import com.example.food.domain.models.CartItem
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import com.example.food.domain.models.PromoCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseDataSource @Inject constructor(
    private val foodDao: FoodDao,
    private val cartItemDao: CartItemDao,
    private val promoCodeDao: PromoCodeDao,
    private val mapper: DataBaseFoodMapper
) : LocalDataSource {
    override suspend fun saveCategories(categories: List<Category>) = withContext(Dispatchers.IO) {
        foodDao.insertCategories(categories.map { mapper.categoryToCategoryEntity(it) })
    }

    override suspend fun saveFoodList(food: List<Food>, categoryId: Long) =
        withContext(Dispatchers.IO) {
            foodDao.insertFood(food.map { mapper.foodToFoodEntity(it, categoryId) })
        }

    override suspend fun saveItemToCart(cartItem: CartItem) = withContext(Dispatchers.IO) {
        val entity = mapper.cartItemToCartItemEntity(cartItem)
        cartItemDao.insertCartItem(entity)
    }

    override suspend fun updateCartItem(foodId: Long, quantity: Int) = withContext(Dispatchers.IO){
        cartItemDao.updateCartItem(foodId, quantity)
    }

    override suspend fun deleteCartItem(cartItem: CartItem) = withContext(Dispatchers.IO){
        cartItemDao.deleteCartItem(mapper.cartItemToCartItemEntity(cartItem))
    }

    override suspend fun getCategories(): List<Category> = withContext(Dispatchers.IO) {
        return@withContext foodDao.getCategories().map { mapper.categoryEntityToCategory(it) }
    }

    override suspend fun getFoodsByCategory(category: Category): List<Food> =
        withContext(Dispatchers.IO) {
            return@withContext foodDao.getFoodByCategoryId(category.id)
                .map { mapper.foodEntityToFood(it) }
        }

    override suspend fun getFoodItem(id: Long): Food = withContext(Dispatchers.IO) {
        val foodItem = foodDao.getFoodItem(id)
        return@withContext mapper.foodEntityToFood(foodItem)
    }

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartItemDao.getCartItems().map {
            it.map { entity ->
                mapper.cartItemEntityToCartItem(entity)
            }
        }
    }

    override suspend fun getPromoCode(couponName: String): PromoCode? =
        withContext(Dispatchers.IO) {
            val promoCodeEntity = promoCodeDao.getPromoCode(couponName)
            return@withContext mapper.promoCodeEntityToPromoCode(promoCodeEntity)
        }

    override suspend fun insertPromoCode(promoCode: PromoCode) = withContext(Dispatchers.IO){
        val entity = mapper.promoCodeToPromoCodeEntity(promoCode)
        promoCodeDao.insertPromoCode(entity)
    }

    override suspend fun deletePromoCode(promoCode: PromoCode) = withContext(Dispatchers.IO){
        val entity = mapper.promoCodeToPromoCodeEntity(promoCode)
        promoCodeDao.deletePromoCode(entity)
    }

    override fun getPromoCodeList(): Flow<List<PromoCode>> {
        return promoCodeDao.getPromoCodeList().map {
            it.map { entity ->
                mapper.promoCodeEntityToPromoCode(entity)
            }
        }
    }
}