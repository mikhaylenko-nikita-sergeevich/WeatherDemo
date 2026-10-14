package com.mikhaylenko.weatherdemo.di

import com.mikhaylenko.weatherdemo.data.location.LocationProviderImpl
import com.mikhaylenko.weatherdemo.domain.location.LocationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationProvider(
        provider: LocationProviderImpl
    ): LocationProvider

}
