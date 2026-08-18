package com.mikhaylenko.weatherdemo.di

import com.mikhaylenko.weatherdemo.data.WeatherRepository
import com.mikhaylenko.weatherdemo.data.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        repository: WeatherRepositoryImpl
    ): WeatherRepository
}