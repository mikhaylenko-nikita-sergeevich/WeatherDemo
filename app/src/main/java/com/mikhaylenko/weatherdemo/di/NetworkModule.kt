package com.mikhaylenko.weatherdemo.di

import com.google.gson.GsonBuilder
import com.mikhaylenko.weatherdemo.BuildConfig
import com.mikhaylenko.weatherdemo.data.WeatherApiKeyInterceptor
import com.mikhaylenko.weatherdemo.data.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dns
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress
import javax.inject.Singleton

private const val WEATHER_API_BASE_URL = "https://api.weatherapi.com/v1/"
private const val WEATHER_API_IP = "79.127.134.226"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

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
        // временный хак из-за проблем доступа к остальным ip вервиса
        .dns { hostname ->
            listOf(InetAddress.getByName(WEATHER_API_IP))
        }
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

}
