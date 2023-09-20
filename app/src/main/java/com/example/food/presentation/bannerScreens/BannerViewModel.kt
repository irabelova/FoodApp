package com.example.food.presentation.bannerScreens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.domain.Repository
import com.example.food.domain.models.PromoCode
import com.example.food.presentation.checkoutScreen.CheckoutViewModel.Companion.SAVE10
import kotlinx.coroutines.launch
import javax.inject.Inject

class BannerViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _isPressed = MutableLiveData(false)
    val isPressed: LiveData<Boolean> = _isPressed

    private val _currentStep = MutableLiveData(0)
    val currentStep: LiveData<Int> = _currentStep

    fun setCurrentStep(isIncreased: Boolean) {
        if (isIncreased) {
            _currentStep.value = _currentStep.value!! + 1
        } else {
            _currentStep.value = _currentStep.value!! - 1
        }
    }

    fun setIsPressedFlag(isPressed: Boolean) {
        _isPressed.value = isPressed
    }

    fun insertPromoCode () {
        viewModelScope.launch {
            try {
                repository.insertPromoCode(PromoCode(couponName = SAVE10, percent = 0.1F))
            } catch (e: Exception) {
                Log.e("BannerViewModel", "error while saving promo code SAVE10", e)
            }
        }
    }

}