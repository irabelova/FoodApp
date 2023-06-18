package com.example.food

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.food.di.DependencyProviders
import com.example.food.presentation.FoodFragment

class MainActivity : AppCompatActivity() {

    private lateinit var dependencyProvider: DependencyProviders

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dependencyProvider = (application as FoodApplication).provider
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FoodFragment()).commit()
    }
}