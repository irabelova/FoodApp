package com.example.food

import com.example.food.domain.DataBaseFoodMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


internal class DataBaseFoodMapperTest {

    private lateinit var mapper: DataBaseFoodMapper

    @Before
    fun setup() {
        mapper = DataBaseFoodMapper()
    }

    @Test
    fun foodToFoodEntity() {
        assertEquals(foodEntity, mapper.foodToFoodEntity(foodModel, categoryModel.id))
    }

    @Test
    fun categoryToCategoryEntity() {
        assertEquals(categoryEntity, mapper.categoryToCategoryEntity(categoryModel))
    }

    @Test
    fun foodEntityToFood() {
        assertEquals(foodModel, mapper.foodEntityToFood(foodEntity))
    }

    @Test
    fun categoryEntityToCategory() {
        assertEquals(categoryModel, mapper.categoryEntityToCategory(categoryEntity))
    }
}