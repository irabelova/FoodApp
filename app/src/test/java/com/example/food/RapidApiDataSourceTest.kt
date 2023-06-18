package com.example.food

import com.example.food.data.network.FoodApiService
import com.example.food.data.network.dto.CategoriesDto
import com.example.food.data.network.dto.FoodResultDto
import com.example.food.domain.NetworkFoodMapper
import com.example.food.domain.RapidApiDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@OptIn(ExperimentalCoroutinesApi::class)
internal class RapidApiDataSourceTest {

    @Mock
    private lateinit var api: FoodApiService

    @Mock
    private lateinit var mapper: NetworkFoodMapper

    private lateinit var dataSource: RapidApiDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dataSource = RapidApiDataSource(api, mapper)
    }

    @Test
    fun `getCategories filter nothing`() = runTest {
        `when`(api.getCategories()).thenReturn(CategoriesDto(listOf(categoryDto)))
        `when`(mapper.categoryDtoToCategory(categoryDto)).thenReturn(categoryModel)
        assertEquals(listOf(categoryModel), dataSource.getCategories())
    }

    @Test
    fun `getCategories filter one item`() = runTest {
        `when`(api.getCategories()).thenReturn(
            CategoriesDto(
                listOf(
                    categoryDto,
                    categoryDto.copy(type = "WRONG_TYPE")
                )
            )
        )
        `when`(mapper.categoryDtoToCategory(categoryDto)).thenReturn(categoryModel)
        assertEquals(listOf(categoryModel), dataSource.getCategories())
    }

    @Test
    fun getFoodsByCategory() = runTest {
        `when`(api.getFoods(tag = categoryModel.name)).thenReturn(FoodResultDto(listOf(foodDto)))
        `when`(mapper.foodDtoToFood(foodDto)).thenReturn(foodModel)
        assertEquals(listOf(foodModel), dataSource.getFoodsByCategory(categoryModel))
    }
}