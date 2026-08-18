package com.mikhaylenko.weatherdemo.data

import com.mikhaylenko.weatherdemo.data.model.WeatherApiResponse

interface WeatherRepository {
    suspend fun getWeatherForecast(): Result<WeatherApiResponse>
}