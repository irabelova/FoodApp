package com.example.food.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PromoCodes")
data class PromoCodeEntity (
    @PrimaryKey
    val couponName: String,
    val percent: Float
)