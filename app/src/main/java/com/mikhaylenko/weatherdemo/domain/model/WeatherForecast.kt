package com.mikhaylenko.weatherdemo.domain.model

data class WeatherForecast(
    val location: WeatherLocation,
    val current: CurrentWeather,
    val dailyForecast: List<DailyForecast>
)

data class WeatherLocation(
    val name: String,
    val country: String,
    val localtimeEpoch: Long
)

data class CurrentWeather(
    val tempC: Double,
    val windKph: Double,
    val condition: WeatherCondition
)

data class WeatherCondition(
    val text: String,
    val iconUrl: String
)

data class DailyForecast(
    val dateEpoch: Long,
    val maxTempC: Double,
    val minTempC: Double,
    val condition: WeatherCondition,
    val hourlyForecast: List<HourlyForecast>
)

data class HourlyForecast(
    val timeEpoch: Long,
    val tempC: Double,
    val condition: WeatherCondition
)
