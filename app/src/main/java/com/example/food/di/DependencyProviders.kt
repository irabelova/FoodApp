package com.example.food.di

import android.content.Context
import androidx.room.Room
import com.example.food.BuildConfig
import com.example.food.data.database.FoodDataBase
import com.example.food.data.network.FoodApiService
import com.example.food.domain.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class DependencyProviders(private val context: Context) {

    val baseUrl = "https://${BuildConfig.API_HOST}/"

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .baseUrl(baseUrl)
        .build()

    val foodApiService: FoodApiService = retrofit.create()

    fun getDataSource(): DataSource = RapidApiDataSource(foodApiService, NetworkFoodMapper())
//    private fun getDataSource(): FakeDataSource = FakeDataSource()

    val database = Room.databaseBuilder(
        context.applicationContext,
        FoodDataBase::class.java,
        "food_database"
    )
        .fallbackToDestructiveMigration()
        .build()
    fun getLocalDataSource(): LocalDataSource = DataBaseDataSource(database.foodDao(), DataBaseFoodMapper())

    fun getRepository(): Repository = Repository(getDataSource(), getLocalDataSource())
}
