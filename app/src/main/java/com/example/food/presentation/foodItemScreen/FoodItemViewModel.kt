package com.example.food.presentation.foodItemScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.presentation.FOOD_ITEM_ID
import com.example.food.di.ViewModelAssistedFactory
import com.example.food.domain.Repository
import com.example.food.domain.models.CartItem
import com.example.food.domain.models.FoodCartItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class FoodItemViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val isLoading = MutableStateFlow(false)
    private val isError = MutableStateFlow(false)

    val id: Long = savedStateHandle[FOOD_ITEM_ID]!!
    private var isAddedToCart = false

    private val cartItemFlow = MutableStateFlow<FoodCartItem?>(null)


    val foodItemStateFlow: Flow<FoodItemUiModel> =
        combine(
            repository.getFoodCartItems(),
            cartItemFlow,
            isLoading,
            isError
        ) { repositoryItems, cartItem, loading, error ->
            when {
                loading -> FoodItemUiModel.Loading
                error -> FoodItemUiModel.Error
                else -> {
                    val found =  repositoryItems.find { it.food.id == id }
                    isAddedToCart = found != null
                    val foodCartItem = found ?: cartItem
                    if (foodCartItem != null) FoodItemUiModel.Data(
                        foodCartItem.food,
                        foodCartItem.cartItem
                    )
                    else FoodItemUiModel.Error

                }
            }
        }

    init {
        loadFoodItem()
    }

    fun loadFoodItem() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                cartItemFlow.value = FoodCartItem(CartItem(0, id, 1), repository.getFoodItem(id))
            } catch (e: Throwable) {
                Log.e("FoodItemViewModel", "load food item error", e)
                isError.value = true
            } finally {
                isLoading.value = false
            }


        }
    }


    fun addFoodItemToShoppingCart() {
        viewModelScope.launch {
            try {
                repository.saveCartItem(cartItemFlow.value!!.cartItem)
            } catch (e: Exception) {
                Log.e("FoodItemViewModel", "add to cart error", e)
            }
        }
    }


    fun changeQuantityCounter(isIncreased: Boolean) {
        val foodCartItem = cartItemFlow.value!!
        val cartItem = foodCartItem.cartItem
        var quantity = cartItem.quantity
        if (isIncreased) {
            quantity++
        } else {
            quantity--
        }
        val updated = cartItem.copy(quantity = quantity)
        if (isAddedToCart) {
            updateCartItem(id, quantity)
        }
            cartItemFlow.value = foodCartItem.copy(cartItem = updated)
    }

    fun clearItem() {
        val old = cartItemFlow.value
        cartItemFlow.value = old?.copy(cartItem = CartItem(0, id, 1))
    }

    private fun updateCartItem(id: Long, quantity: Int) {
        viewModelScope.launch {
            try {
                repository.updateCartItem(id, quantity)
            } catch (e: Exception) {
                Log.e("FoodItemViewModel", "error while updating food item", e)
            }
        }
    }

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<FoodItemViewModel>
}