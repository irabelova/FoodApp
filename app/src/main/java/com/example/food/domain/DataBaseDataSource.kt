package com.example.food.domain

import com.example.food.data.database.FoodDao
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseDataSource @Inject constructor(
    private val foodDao: FoodDao,
//    private val cartItemDao: CartItemDao,
    private val mapper: DataBaseFoodMapper
): LocalDataSource {
    override suspend fun saveCategories(categories: List<Category>) = withContext(Dispatchers.IO){
        foodDao.insertCategories(categories.map { mapper.categoryToCategoryEntity(it) })
    }

    override suspend fun saveFoodList(food: List<Food>) = withContext(Dispatchers.IO){
        foodDao.insertFood(food.map { mapper.foodToFoodEntity(it)})
    }

    override suspend fun getCategories(): List<Category> = withContext(Dispatchers.IO){
        return@withContext foodDao.getCategories().map { mapper.categoryEntityToCategory(it) }
    }

    override suspend fun getFoodsByCategory(category: Category): List<Food> = withContext(Dispatchers.IO){
        return@withContext foodDao.getFoodByCategoryId(category.id).map { mapper.foodEntityToFood(it) }
    }

    override suspend fun getFoodItem(id: Long, categoryId: Long): Food = withContext(Dispatchers.IO){
        val foodItem = foodDao.getFoodItem(id)
        return@withContext mapper.foodEntityToFood(foodItem)
    }

    override suspend fun updateCartItem(foodItem: Food) = withContext(Dispatchers.IO){
        foodDao.updateCartItem(mapper.foodToFoodEntity(foodItem))
    }

    override suspend fun getCartItems(): List<Food> = withContext(Dispatchers.IO){
        return@withContext foodDao.getCartItems().map { mapper.foodEntityToFood(it) }
    }

}