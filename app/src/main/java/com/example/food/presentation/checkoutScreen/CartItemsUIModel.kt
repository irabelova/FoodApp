package com.example.food.presentation.checkoutScreen

import com.example.food.domain.models.FoodCartItem


sealed interface CartItemsUiModel {
    object Loading: CartItemsUiModel
    data class Data(val cartItems: List<FoodCartItem>): CartItemsUiModel {
        val totalQuantityOfItems : Int
            get() {
                var totalQuantity = 0
                cartItems.forEach {
                    totalQuantity += it.cartItem.quantity
                }
                return totalQuantity
            }
        val totalPriceItems : Int
            get() {
                var totalPrice = 0
                cartItems.forEach {
                    totalPrice += it.cartItem.quantity * it.food.price
                }
                return totalPrice
            }
    }
    object Error: CartItemsUiModel
}