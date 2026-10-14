package com.mikhaylenko.weatherdemo.data.mapper

import com.mikhaylenko.weatherdemo.data.model.Condition
import com.mikhaylenko.weatherdemo.data.model.WeatherApiResponse
import com.mikhaylenko.weatherdemo.domain.model.CurrentWeather
import com.mikhaylenko.weatherdemo.domain.model.DailyForecast
import com.mikhaylenko.weatherdemo.domain.model.HourlyForecast
import com.mikhaylenko.weatherdemo.domain.model.WeatherCondition
import com.mikhaylenko.weatherdemo.domain.model.WeatherForecast
import com.mikhaylenko.weatherdemo.domain.model.WeatherLocation

internal fun WeatherApiResponse.toDomain(): WeatherForecast = WeatherForecast(
    location = WeatherLocation(
        name = location.name,
        country = location.country,
        localtimeEpoch = location.localtimeEpoch
    ),
    current = CurrentWeather(
        tempC = current.tempC,
        windKph = current.windKph,
        condition = current.condition.toDomain()
    ),
    dailyForecast = forecast.forecastday.map { forecastDay ->
        DailyForecast(
            dateEpoch = forecastDay.dateEpoch,
            maxTempC = forecastDay.day.maxtempC,
            minTempC = forecastDay.day.mintempC,
            condition = forecastDay.day.condition.toDomain(),
            hourlyForecast = forecastDay.hour.map { hour ->
                HourlyForecast(
                    timeEpoch = hour.timeEpoch,
                    tempC = hour.tempC,
                    condition = hour.condition.toDomain()
                )
            }
        )
    }
)

private fun Condition.toDomain(): WeatherCondition = WeatherCondition(
    text = text,
    iconUrl = if (icon.startsWith("//")) "https:$icon" else icon
)
