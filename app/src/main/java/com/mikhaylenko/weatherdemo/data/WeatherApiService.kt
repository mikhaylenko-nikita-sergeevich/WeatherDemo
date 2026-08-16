package com.mikhaylenko.weatherdemo.data

import com.mikhaylenko.weatherdemo.data.model.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("q") query: String,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
        @Query("lang") lang: String = "ru"
    ): WeatherApiResponse
}