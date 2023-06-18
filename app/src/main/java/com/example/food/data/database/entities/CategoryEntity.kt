package com.example.food.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CATEGORY_TABLE_NAME = "Categories"

@Entity(tableName = CATEGORY_TABLE_NAME)
data class CategoryEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val displayName: String
)
