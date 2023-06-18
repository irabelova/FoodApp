package com.example.food.data.network

import com.example.food.BuildConfig.API_HOST
import com.example.food.BuildConfig.API_KEY
import com.example.food.data.network.dto.CategoriesDto
import com.example.food.data.network.dto.FoodResultDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FoodApiService {

    @GET("tags/list")
    suspend fun getCategories(
        @Header("X-RapidAPI-Key") key: String = API_KEY,
        @Header("X-RapidAPI-Host") host: String = API_HOST,
    ): CategoriesDto

    @GET("recipes/list")
    suspend fun getFoods(
        @Header("X-RapidAPI-Key") key: String = API_KEY,
        @Header("X-RapidAPI-Host") host: String = API_HOST,
        @Query("from") from: Int = 0,
        @Query("size") size: Int = 5,
        @Query("tags") tag: String
    ): FoodResultDto
}