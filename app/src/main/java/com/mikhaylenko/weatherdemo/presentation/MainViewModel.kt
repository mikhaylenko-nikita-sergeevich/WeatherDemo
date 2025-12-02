package com.mikhaylenko.weatherdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikhaylenko.weatherdemo.data.model.CurrentWeather
import com.mikhaylenko.weatherdemo.data.model.Forecastday
import com.mikhaylenko.weatherdemo.data.model.Hour
import com.mikhaylenko.weatherdemo.data.model.Location
import com.mikhaylenko.weatherdemo.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchWeather()
    }

    fun fetchWeather() {
        _weatherState.value = WeatherState.Loading
        viewModelScope.launch {
            repository.getWeatherForecast()
                .onSuccess { response ->
                    val allHours = response.forecast.forecastday.flatMap { it.hour }
                    val upcoming24Hours = allHours.filter {
                        it.timeEpoch >= response.location.localtimeEpoch
                    }.take(24)

                    _weatherState.value = WeatherState.Success(
                        location = response.location,
                        currentWeather = response.current,
                        hourlyForecast = upcoming24Hours,
                        dailyForecast = response.forecast.forecastday
                    )
                }
                .onFailure { error ->
                    _weatherState.value =
                        WeatherState.Error(error.localizedMessage ?: "Unknown error")
                }
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        viewModelScope.launch {
            repository.getWeatherForecast()
                .onSuccess { response ->
                    val allHours = response.forecast.forecastday.flatMap { it.hour }
                    val upcoming24Hours = allHours.filter {
                        it.timeEpoch >= response.location.localtimeEpoch
                    }.take(24)

                    _weatherState.value = WeatherState.Success(
                        location = response.location,
                        currentWeather = response.current,
                        hourlyForecast = upcoming24Hours,
                        dailyForecast = response.forecast.forecastday
                    )
                }
                .onFailure { error ->
                    _weatherState.value =
                        WeatherState.Error(error.localizedMessage ?: "Unknown error")
                }
            _isRefreshing.value = false
        }
    }
}

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(
        val location: Location,
        val currentWeather: CurrentWeather,
        val hourlyForecast: List<Hour>,
        val dailyForecast: List<Forecastday>
    ) : WeatherState()

    data class Error(val message: String) : WeatherState()
}