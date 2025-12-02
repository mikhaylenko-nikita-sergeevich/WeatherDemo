package com.mikhaylenko.weatherdemo.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mikhaylenko.weatherdemo.data.model.CurrentWeather
import com.mikhaylenko.weatherdemo.data.model.Forecastday
import com.mikhaylenko.weatherdemo.data.model.Hour
import com.mikhaylenko.weatherdemo.data.model.Location

@Composable
fun WeatherScreen(
    location: Location,
    currentWeather: CurrentWeather,
    hourlyForecast: List<Hour>,
    dailyForecast: List<Forecastday>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            CurrentWeatherCard(
                location = location,
                currentWeather = currentWeather
            )
        }
        HourlyForecastRow(
            hourlyForecast = hourlyForecast,
            modifier = Modifier.fillMaxWidth()
        )
        DailyForecastList(
            dailyForecast = dailyForecast
        )
    }
}