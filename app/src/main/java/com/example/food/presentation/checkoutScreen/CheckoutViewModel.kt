package com.example.food.presentation.checkoutScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.domain.Repository
import com.example.food.domain.models.Food
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckoutViewModel  @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _cartItemsState = MutableLiveData<CartItemsUiModel>()
    val cartItemsState: LiveData<CartItemsUiModel> = _cartItemsState


    init {
        getCartItems()
    }

    fun changeQuantity(isIncreased: Boolean, foodItem: Food) {
        var quantity = foodItem.quantity
        val data = getData()

        val mutableItems = data.cartItems.toMutableList()
        val index = mutableItems.indexOf(foodItem)
        mutableItems.removeAt(index)
        val updated = if (isIncreased) {
            quantity++
            foodItem.copy(quantity = quantity)
        } else {
            quantity--
            var item = foodItem
            item = if (quantity==0) {
                item.copy(isAddedToCart = false)
            }else {
                foodItem.copy(quantity = quantity)
            }
            item
        }
        updateCartItem(updated)
        if(quantity != 0) {
            mutableItems.add(index, updated)
        }
        _cartItemsState.value = CartItemsUiModel.Data(mutableItems)

    }

//    fun getTotalQuantityOfItems(cartItems: List<Food>): Int {
//        var totalQuantity = 0
//        cartItems.forEach {
//            totalQuantity =+ it.quantity
//        }
//        return totalQuantity
//    }

    private fun updateCartItem(foodItem: Food) {
        viewModelScope.launch {
            try {
                repository.updateCartItem(foodItem)
            } catch (e: Exception) {
                Log.e("FoodItemViewModel", "error while updating food item", e)
            }
        }
    }

    private fun getCartItems() {
        viewModelScope.launch {
            _cartItemsState.value = CartItemsUiModel.Loading
            try {
                _cartItemsState.value = CartItemsUiModel.Data(
                    repository.getCartItems()
                )
            } catch (e: Exception) {
                Log.e("CheckoutViewModel", "", e)
                _cartItemsState.value = CartItemsUiModel.Error
            }
        }
    }

    private fun getData(): CartItemsUiModel.Data = _cartItemsState.value as CartItemsUiModel.Data

}