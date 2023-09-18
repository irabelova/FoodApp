package com.example.food.presentation.checkoutScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.domain.Repository
import com.example.food.domain.models.CartItem
import com.example.food.domain.models.FoodCartItem
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckoutViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val cartItemsStateFlow = repository.getFoodCartItems().map {
        CartItemsUiModel.Data(it)
    }


    fun changeQuantity(isIncreased: Boolean, foodCartItem: FoodCartItem) {
        var quantity = foodCartItem.cartItem.quantity
        if (isIncreased) {
            quantity++
        } else {
            quantity--

        }
        if (quantity == 0) {
            deleteCartItem(foodCartItem.cartItem)
        } else {
            updateCartItem(foodCartItem.cartItem.foodId, quantity)
        }
    }

    private fun deleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                repository.deleteCartItem(cartItem)
            } catch (e: Exception) {
                Log.e("FoodItemViewModel", "error while deleting food item", e)
            }
        }
    }

    private fun updateCartItem(foodId: Long, quantity: Int) {
        viewModelScope.launch {
            try {
                repository.updateCartItem(foodId, quantity)
            } catch (e: Exception) {
                Log.e("FoodItemViewModel", "error while updating food item", e)
            }
        }
    }
}