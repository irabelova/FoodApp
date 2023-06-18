package com.example.food

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.food.domain.Repository
import com.example.food.presentation.CategoryState
import com.example.food.presentation.FoodState
import com.example.food.presentation.FoodViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@OptIn(ExperimentalCoroutinesApi::class)
internal class FoodViewModelTest {

    @Mock
    private lateinit var repository: Repository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    fun createViewModel() = FoodViewModel(repository)

    @Test
    fun `initialLoading error`() = runTest {
        `when`(repository.getCategories()).thenThrow(RuntimeException())
        val viewModel = createViewModel()
        assertEquals(CategoryState.InitialLoadingError, viewModel.categoryState.value)
    }

    @Test
    fun `initialLoading error cause empty categories`() = runTest {
        `when`(repository.getCategories()).thenReturn(emptyList())
        val viewModel = createViewModel()
        assertEquals(CategoryState.InitialLoadingError, viewModel.categoryState.value)
    }

    @Test
    fun `loading foods error`() = runTest {
        `when`(repository.getCategories()).thenReturn(listOf(categoryModel))
        `when`(repository.getFoods(categoryModel)).thenThrow(RuntimeException())
        val viewModel = createViewModel()
        assertEquals(
            CategoryState.CategoryData(listOf(categoryModel)),
            viewModel.categoryState.value
        )
        assertEquals(FoodState.Error, viewModel.foodState.value)
        assertEquals(categoryModel, viewModel.selectedCategory.value)
    }

    @Test
    fun `loading foods success`() = runTest {
        `when`(repository.getCategories()).thenReturn(listOf(categoryModel))
        `when`(repository.getFoods(categoryModel)).thenReturn(listOf(foodModel))
        val viewModel = createViewModel()
        assertEquals(
            CategoryState.CategoryData(listOf(categoryModel)),
            viewModel.categoryState.value
        )
        assertEquals(FoodState.FoodData(listOf(foodModel)), viewModel.foodState.value)
        assertEquals(categoryModel, viewModel.selectedCategory.value)
    }

    @Test
    fun changeCategory() = runTest {
        `when`(repository.getCategories()).thenReturn(listOf(categoryModel))
        `when`(repository.getFoods(categoryModel)).thenThrow(RuntimeException())
        val newCategory = categoryModel.copy(id = 999999)
        `when`(repository.getFoods(newCategory)).thenReturn(listOf(foodModel))
        val viewModel = createViewModel()
        assertEquals(
            CategoryState.CategoryData(listOf(categoryModel)),
            viewModel.categoryState.value
        )
        assertEquals(FoodState.Error, viewModel.foodState.value)
        assertEquals(categoryModel, viewModel.selectedCategory.value)
        viewModel.changeCategory(newCategory)
        assertEquals(FoodState.FoodData(listOf(foodModel)), viewModel.foodState.value)
        assertEquals(newCategory, viewModel.selectedCategory.value)
    }
}