package com.example.food.presentation.foodItemScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.CATEGORY_ID
import com.example.food.FOOD_ITEM_ID
import com.example.food.di.ViewModelAssistedFactory
import com.example.food.domain.Repository
import com.example.food.domain.models.Food
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class FoodItemViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _foodItemState = MutableLiveData<FoodItemUiModel>()
    val foodItemState: LiveData<FoodItemUiModel> = _foodItemState

    val id: Long = savedStateHandle[FOOD_ITEM_ID]!!
    val categoryId: Long = savedStateHandle[CATEGORY_ID]!!


    init {
        getFoodItem()
    }

    fun addFoodItemToShoppingCart(foodItem: Food) {
        viewModelScope.launch {
            try {
                _foodItemState.value = FoodItemUiModel.Data(foodItem)
                repository.updateCartItem(foodItem)
            } catch (e: Exception) {
                Log.e("FoodItemViewModel", "add to cart error", e)
            }
        }
    }

    fun getFoodItem() {
        viewModelScope.launch {
            _foodItemState.value = FoodItemUiModel.Loading
            try {
                val foodItem = repository.getFoodItem(id, categoryId)
                _foodItemState.value = FoodItemUiModel.Data(foodItem)
            } catch (e: Exception) {
                Log.e("FoodItemViewModel", "", e)
                _foodItemState.value = FoodItemUiModel.Error
            }
        }
    }

    fun changeQuantityCounter(isIncreased: Boolean, foodItem: Food) {
        var quantity = foodItem.quantity
        if (isIncreased) {
            _foodItemState.value = FoodItemUiModel.Data(foodItem.copy(quantity = quantity+1))
            quantity++
        } else {
            _foodItemState.value = FoodItemUiModel.Data(foodItem.copy(quantity = quantity-1))
            quantity--
        }
        if (foodItem.isAddedToCart) {
            updateCartItem(foodItem.copy(quantity = quantity))
        }
    }

    private fun updateCartItem(foodItem: Food) {
        viewModelScope.launch {
            try {
                repository.updateCartItem(foodItem)
            } catch (e: Exception) {
                Log.e("FoodItemViewModel", "error while updating food item", e)
            }
        }
    }

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<FoodItemViewModel>
}