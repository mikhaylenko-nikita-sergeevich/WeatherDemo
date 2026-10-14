package com.mikhaylenko.weatherdemo.domain.network

import com.mikhaylenko.weatherdemo.domain.model.WeatherForecast
import com.mikhaylenko.weatherdemo.domain.WeatherResult

interface WeatherRepository {
    suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): WeatherResult<WeatherForecast>
}
