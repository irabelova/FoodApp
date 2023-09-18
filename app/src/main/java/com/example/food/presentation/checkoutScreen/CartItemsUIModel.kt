package com.example.food.presentation.checkoutScreen

import com.example.food.domain.models.Food


sealed interface CartItemsUiModel {
    object Loading: CartItemsUiModel
    data class Data(val cartItems: List<Food>): CartItemsUiModel {
        val totalQuantityOfItems : Int
            get() {
                var totalQuantity = 0
                cartItems.forEach {
                    totalQuantity += it.quantity
                }
                return totalQuantity
            }
        val totalPriceItems : Int
            get() {
                var totalPrice = 0
                cartItems.forEach {
                    totalPrice += it.quantity * it.price
                }
                return totalPrice
            }
    }
    object Error: CartItemsUiModel
}