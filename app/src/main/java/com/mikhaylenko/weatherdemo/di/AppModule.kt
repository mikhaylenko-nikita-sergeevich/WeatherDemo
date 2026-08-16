package com.mikhaylenko.weatherdemo.di

import com.google.gson.GsonBuilder
import com.mikhaylenko.weatherdemo.BuildConfig
import com.mikhaylenko.weatherdemo.data.WeatherApiKeyInterceptor
import com.mikhaylenko.weatherdemo.data.WeatherApiService
import com.mikhaylenko.weatherdemo.data.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val WEATHER_API_BASE_URL = "https://api.weatherapi.com/v1/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApiKeyInterceptor() = WeatherApiKeyInterceptor(
        apiKey = BuildConfig.WEATHER_API_KEY
    )

    @Provides
    @Singleton
    fun provideOkHttpClient(
        apiKeyInterceptor: WeatherApiKeyInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(WEATHER_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        )
        .build()

    @Provides
    @Singleton
    fun provideWeatherApiService(
        retrofit: Retrofit
    ): WeatherApiService = retrofit.create(WeatherApiService::class.java)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        apiService: WeatherApiService
    ) = WeatherRepository(apiService = apiService)

}
