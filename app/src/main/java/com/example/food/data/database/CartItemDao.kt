package com.example.food.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.food.data.database.entities.CartItemEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CartItemDao {
    @Insert
    suspend fun insertCartItem(cartItem: CartItemEntity)

    @Query("UPDATE CartItems SET quantity=:quantity WHERE foodId=:foodId")
    suspend fun updateCartItem(foodId: Long, quantity: Int)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItemEntity)


    @Query("SELECT * FROM CartItems")
    fun getCartItems(): Flow<List<CartItemEntity>>

}