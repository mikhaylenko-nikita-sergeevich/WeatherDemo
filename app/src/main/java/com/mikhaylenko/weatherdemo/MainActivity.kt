package com.mikhaylenko.weatherdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.mikhaylenko.weatherdemo.presentation.WeatherState
import com.mikhaylenko.weatherdemo.presentation.WeatherViewModel
import com.mikhaylenko.weatherdemo.presentation.ui.CurrentWeatherCard
import com.mikhaylenko.weatherdemo.presentation.ui.DailyForecastList
import com.mikhaylenko.weatherdemo.presentation.ui.ErrorDialog
import com.mikhaylenko.weatherdemo.presentation.ui.HourlyForecastRow
import com.mikhaylenko.weatherdemo.ui.theme.WeatherDemoTheme
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
                WeatherScreen(
                    state = state,
                    onRetry = { viewModel.fetchWeather() }
                )
            }
        }
    }
}

@Composable
fun WeatherScreen(state: WeatherState, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.0f)
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is WeatherState.Loading -> {
                    CircularProgressIndicator()
                }

                is WeatherState.Success -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()) {
                            CurrentWeatherCard(location = state.location, currentWeather = state.currentWeather)
                        }
                        HourlyForecastRow(
                            hourlyForecast = state.hourlyForecast,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp)) // Spacing between hourly and daily
                        DailyForecastList(
                            dailyForecast = state.dailyForecast,
                            modifier = Modifier.weight(1f) // Daily forecast takes remaining space
                        )
                        Spacer(modifier = Modifier.height(16.dp)) // Bottom padding for the entire content
                    }
                }

                is WeatherState.Error -> {
                    var showError by remember { mutableStateOf(true) }
                    if (showError) {
                        ErrorDialog(
                            errorMessage = state.message,
                            onRetry = {
                                showError = false
                                onRetry()
                            },
                            onDismiss = { showError = false }
                        )
                    }
                }
            }
        }
    }
}