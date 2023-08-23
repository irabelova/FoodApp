package com.example.food.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.domain.Repository
import com.example.food.domain.models.Category
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoodViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _categoryState = MutableLiveData<CategoryState>()
    val categoryState: LiveData<CategoryState> = _categoryState

    private val _foodState = MutableLiveData<FoodState>()
    val foodState: LiveData<FoodState> = _foodState

    private val _selectedCategory = MutableLiveData<Category>()
    val selectedCategory: LiveData<Category> = _selectedCategory

    private val _selectedCity = MutableLiveData("Moscow")
    val selectedCity: LiveData<String> = _selectedCity

    private val _clickedBannerIndex = MutableLiveData(0)
    val clickedBannerIndex: LiveData<Int> = _clickedBannerIndex

    init {
        initialLoading()
    }

    fun changeCity(selectedCity: String) {
        _selectedCity.value = selectedCity
    }

    fun changeBannerIndex(clickedBannerIndex: Int) {
        _clickedBannerIndex.value = clickedBannerIndex
    }

    fun initialLoading() {
        viewModelScope.launch {
            _categoryState.value = CategoryState.InitialLoading
            try {
                val categories = repository.getCategories()
                val selectedCategory = categories[0]
                _categoryState.value = CategoryState.CategoryData(
                    categories = categories,
                )
                _selectedCategory.value = selectedCategory
                _foodState.value = loadFoods(selectedCategory)

            } catch (ex: Exception) {
                Log.e("FoodViewModel", "loading categories error", ex)
                _categoryState.value = CategoryState.InitialLoadingError
            }

        }
    }

    fun changeCategory(category: Category) {
        _selectedCategory.value = category
        viewModelScope.launch {
            _foodState.value = loadFoods(category)
        }
    }


    fun reloadFoods () {
        viewModelScope.launch {
            _foodState.value = loadFoods(selectedCategory.value!!)
        }
    }

    private suspend fun loadFoods(category: Category): FoodState {
        _foodState.value = FoodState.Loading
        return try {
            FoodState.FoodData(repository.getFoods(category))
        } catch (ex: Exception) {
            Log.e("FoodViewModel", "loading foods error", ex)
            FoodState.Error
        }
    }

}