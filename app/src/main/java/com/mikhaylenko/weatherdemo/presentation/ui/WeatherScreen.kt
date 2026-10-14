package com.mikhaylenko.weatherdemo.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mikhaylenko.weatherdemo.presentation.WeatherState

@Composable
fun WeatherScreen(
    state: WeatherState.Content,
    onRefresh: () -> Unit
) {
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
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
                    location = state.forecast.location,
                    currentWeather = state.forecast.currentWeather
                )
            }
            HourlyForecastRow(
                hourlyForecast = state.forecast.hourlyForecast,
                modifier = Modifier.fillMaxWidth()
            )
            DailyForecastList(
                dailyForecast = state.forecast.dailyForecast
            )
        }
    }
}