package com.example.food.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.food.data.database.entities.PromoCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PromoCodeDao {

    @Insert
    suspend fun insertPromoCode(promoCode: PromoCodeEntity)

    @Delete
    suspend fun deletePromoCode(promoCode: PromoCodeEntity)

    @Query("SELECT * FROM PromoCodes")
    fun getPromoCodeList(): Flow<List<PromoCodeEntity>>
}