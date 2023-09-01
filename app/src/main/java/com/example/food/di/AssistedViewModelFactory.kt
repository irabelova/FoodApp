package com.example.food.di

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.savedstate.SavedStateRegistryOwner

inline fun <reified VM : ViewModel> NavBackStackEntry.assistedViewModel(
    crossinline viewModelProducer: (SavedStateHandle) -> VM,
): Lazy<VM> = lazy {
    ViewModelProvider(
        this,
        createSavedStateViewModelFactory(arguments, viewModelProducer)
    )[VM::class.java]
}

@PublishedApi
internal inline fun <reified VM : ViewModel> SavedStateRegistryOwner.createSavedStateViewModelFactory(
    arguments: Bundle?,
    crossinline creator: (SavedStateHandle) -> VM,
): ViewModelProvider.Factory = object : AbstractSavedStateViewModelFactory(this@createSavedStateViewModelFactory, arguments) {
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(
        key: String,
        modelClass: Class<VM>,
        handle: SavedStateHandle,
    ): VM = creator(handle) as VM
}