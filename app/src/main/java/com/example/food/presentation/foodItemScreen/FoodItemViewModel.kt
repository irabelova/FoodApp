package com.example.food.presentation.foodItemScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.FOOD_ITEM_ID
import com.example.food.di.ViewModelAssistedFactory
import com.example.food.domain.Repository
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

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    val id: Long = savedStateHandle[FOOD_ITEM_ID]!!


    init {
        getFoodItem()
    }


    fun getFoodItem() {
        viewModelScope.launch {
            _foodItemState.value = FoodItemUiModel.Loading
            try {
                _foodItemState.value = FoodItemUiModel.Data(repository.getFoodItem(id))

            } catch (e: Exception) {
                Log.e("FoodItemViewModel", "", e)
                _foodItemState.value = FoodItemUiModel.Error
            }
        }
    }

    fun changeQuantityCounter(isIncreased: Boolean) {
        if (isIncreased) {
            _quantity.value = _quantity.value!! + 1
        }
        else {
            _quantity.value = _quantity.value!! - 1
        }
    }

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<FoodItemViewModel>
}