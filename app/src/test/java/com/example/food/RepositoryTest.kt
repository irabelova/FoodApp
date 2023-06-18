package com.example.food

import com.example.food.domain.DataSource
import com.example.food.domain.LocalDataSource
import com.example.food.domain.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


@OptIn(ExperimentalCoroutinesApi::class)
internal class RepositoryTest {

    @Mock
    private lateinit var dataSource: DataSource
    @Mock
    private lateinit var localDataSource: LocalDataSource

    private lateinit var repository: Repository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = Repository(dataSource, localDataSource)
    }

    @Test(expected = RuntimeException::class)
    fun `getCategories both data sources throws exceptions`() = runTest {
        `when`(dataSource.getCategories()).thenThrow(RuntimeException())
        `when`(localDataSource.getCategories()).thenThrow(RuntimeException())
        repository.getCategories()
    }

    @Test
    fun `getCategories only remote data source throws exception`() = runTest {
        `when`(dataSource.getCategories()).thenThrow(RuntimeException())
        `when`(localDataSource.getCategories()).thenReturn(listOf(categoryModel))
        val result = repository.getCategories()
        verify(localDataSource, times(0)).saveCategories(listOf(categoryModel))
        assertEquals(listOf(categoryModel), result)
    }

    @Test
    fun `getCategories remote data source return data`() = runTest {
        `when`(dataSource.getCategories()).thenReturn(listOf(categoryModel))
        val result = repository.getCategories()
        verify(localDataSource, times(1)).saveCategories(listOf(categoryModel))
        verify(localDataSource, times(0)).getCategories()
        assertEquals(listOf(categoryModel), result)
    }

    @Test(expected = RuntimeException::class)
    fun `getFoodsByCategory both data sources throws exceptions`() = runTest {
        `when`(dataSource.getFoodsByCategory(categoryModel)).thenThrow(RuntimeException())
        `when`(localDataSource.getFoodsByCategory(categoryModel)).thenThrow(RuntimeException())
        repository.getFoods(categoryModel)
    }

    @Test
    fun `getFoodsByCategory only remote data source throws exception`() = runTest {
        `when`(dataSource.getFoodsByCategory(categoryModel)).thenThrow(RuntimeException())
        `when`(localDataSource.getFoodsByCategory(categoryModel)).thenReturn(listOf(foodModel))
        val result = repository.getFoods(categoryModel)
        verify(localDataSource, times(0)).saveFoodList(listOf(foodModel), categoryModel.id)
        assertEquals(listOf(foodModel), result)
    }

    @Test
    fun `getFoodsByCategory remote data source return data`() = runTest {
        `when`(dataSource.getFoodsByCategory(categoryModel)).thenReturn(listOf(foodModel))
        val result = repository.getFoods(categoryModel)
        verify(localDataSource, times(1)).saveFoodList(listOf(foodModel), categoryModel.id)
        verify(localDataSource, times(0)).getFoodsByCategory(categoryModel)
        assertEquals(listOf(foodModel), result)
    }
}