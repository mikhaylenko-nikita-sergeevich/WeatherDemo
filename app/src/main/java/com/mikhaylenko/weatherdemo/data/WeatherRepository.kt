package com.mikhaylenko.weatherdemo.data

import com.mikhaylenko.weatherdemo.data.model.WeatherApiResponse

private const val MOSCOW_LAT_LON = "55.7569,37.6151"
private const val DAYS_FORECAST = 3

class WeatherRepository(
    private val apiService: WeatherApiService
) {

    suspend fun getWeatherForecast(): Result<WeatherApiResponse> = try {
        val response = apiService.getWeatherForecast(
            query = MOSCOW_LAT_LON,
            days = DAYS_FORECAST
        )
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e)
    }
}