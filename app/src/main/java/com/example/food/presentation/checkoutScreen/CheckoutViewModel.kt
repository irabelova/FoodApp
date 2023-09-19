package com.example.food.presentation.checkoutScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.domain.Repository
import com.example.food.domain.models.CartItem
import com.example.food.domain.models.FoodCartItem
import com.example.food.domain.models.PromoCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckoutViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _promoCode = MutableLiveData("")
    val promoCode: LiveData<String> = _promoCode

    private val _isPromoCodeValid = MutableLiveData(true)
    val isPromoCodeValid: LiveData<Boolean> = _isPromoCodeValid

    private val cartItemsStateFlow = repository.getFoodCartItems()
    private val promoCodeFlow = repository.getPromoCodeList()

    val stateFlow: Flow<CartItemsUiModel.Data> =
        combine(
            promoCodeFlow,
            cartItemsStateFlow
        ) { promoCodeList, cartItems ->
            CartItemsUiModel.Data(cartItems, promoCodeList)
        }.catch {
            CartItemsUiModel.Error
        }

    fun applyCode() {
        when (val code = promoCode.value) {
            SAVE10 -> {
                insertPromoCode(PromoCode(code, 0.1F))
                _isPromoCodeValid.value = true
            }
            NEW15 -> {
                insertPromoCode(PromoCode(code, 0.15F))
                _isPromoCodeValid.value = true
            }
            else -> _isPromoCodeValid.value = false
        }
    }

    fun setPromoCode(code: String) {
        _promoCode.value = code
    }

    fun deletePromoCode(promoCode: PromoCode) {
        viewModelScope.launch {
            try {
                repository.deletePromoCode(promoCode)
            } catch (e: Exception) {
                Log.e("CheckoutViewModel", "error while deleting promo code", e)
            }
        }
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

    private fun insertPromoCode (promoCode: PromoCode) {
        viewModelScope.launch {
            try {
                repository.insertPromoCode(promoCode)
            } catch (e: Exception) {
                Log.e("CheckoutViewModel", "error while saving promo code", e)
            }
        }
    }

    private fun deleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                repository.deleteCartItem(cartItem)
            } catch (e: Exception) {
                Log.e("CheckoutViewModel", "error while deleting food item", e)
            }
        }
    }

    private fun updateCartItem(foodId: Long, quantity: Int) {
        viewModelScope.launch {
            try {
                repository.updateCartItem(foodId, quantity)
            } catch (e: Exception) {
                Log.e("CheckoutViewModel", "error while updating food item", e)
            }
        }
    }

    private companion object {
        const val SAVE10 = "SAVE10"
        const val NEW15 = "NEW15"
    }
}