package com.example.food.di

import android.content.Context
import com.example.food.presentation.FoodFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DomainModule::class, ViewModelFactoryModule::class, ViewModelModule::class]
)
interface AppComponent {

    fun inject(foodFragment: FoodFragment)

    @Component.Factory
    interface AppComponentFactory {

        fun create(@BindsInstance context: Context): AppComponent
    }


}