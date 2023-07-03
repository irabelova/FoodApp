package com.example.food

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.food.di.AppComponent
import com.example.food.di.DaggerAppComponent
import com.example.food.presentation.FoodFragment

class MainActivity : AppCompatActivity() {

    lateinit var component: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerAppComponent.factory().create(applicationContext)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FoodFragment())
            .commit()
    }
}