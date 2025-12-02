package com.mikhaylenko.weatherdemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mikhaylenko.weatherdemo.presentation.ui.CurrentWeatherCard
import com.mikhaylenko.weatherdemo.presentation.ui.DailyForecastList
import com.mikhaylenko.weatherdemo.presentation.ui.ErrorDialog
import com.mikhaylenko.weatherdemo.presentation.ui.HourlyForecastRow
import com.mikhaylenko.weatherdemo.presentation.theme.WeatherDemoTheme
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
                WeatherScreen(
                    state = state,
                    isRefreshing = isRefreshing,
                    onRefresh = viewModel::refresh,
                    onRetry = { viewModel.fetchWeather() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CurrentWeatherCard(
                            location = state.location,
                            currentWeather = state.currentWeather
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        HourlyForecastRow(
                            hourlyForecast = state.hourlyForecast,
                            modifier = Modifier.fillMaxWidth()
                        )
                        DailyForecastList(
                            dailyForecast = state.dailyForecast
                        )
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
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        // Empty box for now, dialog handles error
                    }
                }
            }
        }
    }
}