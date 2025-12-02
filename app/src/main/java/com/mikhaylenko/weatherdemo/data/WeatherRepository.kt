package com.mikhaylenko.weatherdemo.data

import com.google.gson.GsonBuilder
import com.mikhaylenko.weatherdemo.data.model.WeatherApiResponse
import com.mikhaylenko.weatherdemo.data.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.weatherapi.com/v1/"
private const val API_KEY = "fa8b3df74d4042b9aa7135114252304"
private const val MOSCOW_LAT_LON = "55.7569,37.6151"
private const val DAYS_FORECAST = 3

class WeatherRepository() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        )
        .build()

    private val apiService = retrofit.create(WeatherApiService::class.java)

    suspend fun getWeatherForecast(): Result<WeatherApiResponse> = try {
        val response = apiService.getWeatherForecast(
            apiKey = API_KEY,
            query = MOSCOW_LAT_LON,
            days = DAYS_FORECAST
        )
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e)
    }


}