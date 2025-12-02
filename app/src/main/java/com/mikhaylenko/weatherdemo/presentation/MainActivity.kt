package com.mikhaylenko.weatherdemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mikhaylenko.weatherdemo.presentation.theme.WeatherDemoTheme
import com.mikhaylenko.weatherdemo.presentation.ui.ErrorScreen
import com.mikhaylenko.weatherdemo.presentation.ui.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherDemoTheme {
                val state by viewModel.weatherState.collectAsState()
                val isRefreshing by viewModel.isRefreshing.collectAsState()
                MainScreen(
                    state = state,
                    isRefreshing = isRefreshing,
                    onRefresh = viewModel::refreshWeather,
                    onRetry = { viewModel.loadWeather() }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    state: WeatherState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            when (state) {
                is WeatherState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is WeatherState.Success -> {
                    WeatherScreen(
                        location = state.location,
                        currentWeather = state.currentWeather,
                        hourlyForecast = state.hourlyForecast,
                        dailyForecast = state.dailyForecast
                    )
                }

                is WeatherState.Error -> {
                    ErrorScreen(
                        errorMessage = state.message,
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}