package com.example.food

import com.example.food.data.database.FoodDao
import com.example.food.domain.DataBaseDataSource
import com.example.food.domain.DataBaseFoodMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
internal class DataBaseDataSourceTest {

    @Mock
    private lateinit var dao: FoodDao

    @Mock
    private lateinit var mapper: DataBaseFoodMapper
    private lateinit var dataSource: DataBaseDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dataSource = DataBaseDataSource(dao, mapper)
    }

    @Test
    fun saveCategories() = runTest {
        `when`(mapper.categoryToCategoryEntity(categoryModel)).thenReturn(categoryEntity)
        dataSource.saveCategories(listOf(categoryModel))
        verify(dao, times(1)).insertCategories(listOf(categoryEntity))
    }

    @Test
    fun saveFoodList() = runTest {
        `when`(mapper.foodToFoodEntity(foodModel, categoryModel.id)).thenReturn(foodEntity)
        dataSource.saveFoodList(listOf(foodModel), categoryModel.id)
        verify(dao, times(1)).insertFood(listOf(foodEntity))
    }

    @Test
    fun getCategories() = runTest {
        `when`(mapper.categoryEntityToCategory(categoryEntity)).thenReturn(categoryModel)
        `when`(dao.getCategories()).thenReturn(listOf(categoryEntity))
        assertEquals(listOf(categoryModel), dataSource.getCategories())
    }

    @Test
    fun getFoodsByCategory() = runTest {
        `when`(mapper.foodEntityToFood(foodEntity)).thenReturn(foodModel)
        `when`(dao.getFoodByCategoryId(categoryModel.id)).thenReturn(listOf(foodEntity))
        assertEquals(listOf(foodModel), dataSource.getFoodsByCategory(categoryModel))
    }
}