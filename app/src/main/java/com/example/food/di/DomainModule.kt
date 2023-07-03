package com.example.food.di

import com.example.food.domain.*
import dagger.Binds
import dagger.Module

@Module(includes = [DataBaseModule::class, NetworkModule::class])
abstract class DomainModule {

    @Binds
    abstract fun bindLocalDataSource(source: DataBaseDataSource): LocalDataSource

    @Binds
    abstract fun bindDataSource(source: RapidApiDataSource): DataSource
}