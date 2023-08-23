package com.example.food.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

const val FOOD_TABLE_NAME = "Food"
@Entity(
    tableName = FOOD_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class FoodEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val thumbnailUrl: String,
    val description: String,
    val price: Int,
    val categoryId: Long
)
