package com.example.food.presentation.checkoutScreen

import com.example.food.domain.models.FoodCartItem
import com.example.food.domain.models.PromoCode


sealed interface CartItemsUiModel {
    object Loading : CartItemsUiModel
    data class Data(val cartItems: List<FoodCartItem>, val promoCodes: List<PromoCode>) :
        CartItemsUiModel {
        val totalQuantityOfItems: Int
            get() {
                var totalQuantity = 0
                cartItems.forEach {
                    totalQuantity += it.cartItem.quantity
                }
                return totalQuantity
            }
        val totalPriceItems: Float
            get() {
                var totalPrice = 0.0F
                cartItems.forEach {
                    totalPrice += it.cartItem.quantity * it.food.price
                }
                return totalPrice
            }
        val deliveryCharges: Float
            get() {
                return if (totalPriceItems < 50.0F) {
                    20.0F
                } else {
                    0.0F
                }
            }
        val couponDiscount: Float
            get() {
                var discount = 0.0F
                promoCodes.forEach {
                    discount += totalPriceItems * it.percent
                }
                return discount
            }
        val totalAmountPayable: Float
            get() {
                return totalPriceItems + deliveryCharges - couponDiscount
            }
    }

    object Error : CartItemsUiModel
}