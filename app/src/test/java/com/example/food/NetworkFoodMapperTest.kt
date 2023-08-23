package com.example.food

import com.example.food.domain.NetworkFoodMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


internal class NetworkFoodMapperTest {

    private lateinit var mapper: NetworkFoodMapper

    @Before
    fun setup() {
        mapper = NetworkFoodMapper()
    }

    @Test
    fun categoryDtoToCategory() {
        assertEquals(categoryModel, mapper.categoryDtoToCategory(categoryDto))
    }

    @Test
    fun foodDtoToFood() {
        assertEquals(foodModel, mapper.foodDtoToFood(foodDto))
    }

    @Test
    fun `foodDtoToFood with null field`() {
        assertEquals(foodModel.copy(price = 0), mapper.foodDtoToFood(foodDto.copy(price = null)))
    }
}