package com.example.food

import android.app.Application
import com.example.food.di.DependencyProviders

class FoodApplication: Application() {
    lateinit var provider: DependencyProviders

    override fun onCreate() {
        super.onCreate()
        provider = DependencyProviders(applicationContext)
    }
}