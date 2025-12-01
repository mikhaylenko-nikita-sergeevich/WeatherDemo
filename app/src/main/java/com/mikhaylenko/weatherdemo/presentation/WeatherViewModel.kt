package com.mikhaylenko.weatherdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikhaylenko.weatherdemo.data.model.WeatherApiResponse
import com.mikhaylenko.weatherdemo.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState

    init {
        fetchWeather()
    }

    fun fetchWeather() {
        _weatherState.value = WeatherState.Loading
        viewModelScope.launch {
            repository.getWeatherForecast()
                .onSuccess { response ->
                    _weatherState.value = WeatherState.Success(response)
                }
                .onFailure { error ->
                    _weatherState.value = WeatherState.Error(error.localizedMessage ?: "Unknown error")
                }
        }
    }
}

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val weather: WeatherApiResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
}