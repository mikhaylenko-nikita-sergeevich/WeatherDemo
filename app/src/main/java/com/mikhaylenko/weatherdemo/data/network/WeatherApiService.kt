package com.mikhaylenko.weatherdemo.data.network

import com.mikhaylenko.weatherdemo.data.model.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") apiKey: String,
        @Query("q") query: String,              // "latitude,longitude"
        @Query("days") days: Int = 3,           // Default to 3 days forecast as per requirement
        @Query("aqi") aqi: String = "no",       // Air Quality Index, not strictly needed for basic weather
        @Query("alerts") alerts: String = "no"  // Weather alerts, not strictly needed for basic weather
    ): WeatherApiResponse
}