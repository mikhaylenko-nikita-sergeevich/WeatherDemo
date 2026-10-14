package com.mikhaylenko.weatherdemo.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikhaylenko.weatherdemo.domain.GetWeatherForLocationUseCase
import com.mikhaylenko.weatherdemo.domain.WeatherError
import com.mikhaylenko.weatherdemo.domain.WeatherResult
import com.mikhaylenko.weatherdemo.domain.model.CurrentWeather
import com.mikhaylenko.weatherdemo.domain.model.DailyForecast
import com.mikhaylenko.weatherdemo.domain.model.HourlyForecast
import com.mikhaylenko.weatherdemo.domain.model.WeatherLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mikhaylenko.weatherdemo.domain.model.WeatherForecast as DomainWeatherForecast

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherForLocation: GetWeatherForLocationUseCase,
    private val savedStateHandle: SavedStateHandle//todo добавить сохранение полного состояния
) : ViewModel() {

    private var loadJob: Job? = null

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _events = MutableSharedFlow<WeatherEvent>(replay = 0)
    val events: SharedFlow<WeatherEvent> = _events.asSharedFlow()

    init {
        //val initialState: WeatherState? = savedStateHandle["content"]
        //if ( initialState != null) _weatherState.value = initialState
        //else load()
        load()
    }

    fun onRetry() {
        load()
    }

    fun onRefresh() {
        load()
    }

    fun onLocationAccessGranted() {
        load()
    }

    fun onPermissionDenied() {
        load(true)
    }

    private fun load(useDefaultCity: Boolean = false) {
        if (loadJob?.isActive == true) return

        _weatherState.update { state ->
            when (state) {
                is WeatherState.Content -> state.copy(isRefreshing = true)
                else -> WeatherState.Loading
            }
        }

        loadJob = viewModelScope.launch {
            when (val result = getWeatherForLocation(useDefaultCity)) {
                is WeatherResult.Success -> onLoadSuccess(result.data)
                is WeatherResult.Failure -> onLoadError(result.error)
            }
            //savedStateHandle["content"] = _weatherState.value
        }
    }

    private fun onLoadSuccess(forecast: DomainWeatherForecast) {
        val upcoming24Hours = forecast.dailyForecast
            .flatMap { it.hourlyForecast }
            .filter { it.timeEpoch >= forecast.location.localtimeEpoch }
            .take(24)

        _weatherState.value = WeatherState.Content(
            isRefreshing = false,
            forecast = WeatherForecast(
                location = forecast.location,
                currentWeather = forecast.current,
                hourlyForecast = upcoming24Hours,
                dailyForecast = forecast.dailyForecast
            )
        )
    }

    private suspend fun onLoadError(error: WeatherError) {
        _weatherState.update { state ->
            when (state) {
                is WeatherState.Content -> {
                    emitError(error)
                    state.copy(isRefreshing = false)
                }
                else -> WeatherState.Error(error)
            }
        }
    }

    private suspend fun emitError(error: WeatherError) {
        _events.emit(WeatherEvent.ShowError(when(error) {
            is WeatherError.LocationUnavailable -> "Location unavailable"
            is WeatherError.NoPermission -> "No geo permission"
            is WeatherError.InvalidResponse -> "Invalid response"
            is WeatherError.NetworkFailure -> "Network failure"
            is WeatherError.NoInternet -> "No internet"
            is WeatherError.Timeout -> "Timeout"
            is WeatherError.Unknown -> "Unknown error"
            is WeatherError.Http -> "Http error ${error.statusCode}"
        }))
    }
}

sealed interface WeatherState {
    object Loading : WeatherState
    data class Content(
        val isRefreshing: Boolean,
        val forecast: WeatherForecast
    ) : WeatherState

    data class Error(val error: WeatherError) : WeatherState
}

data class WeatherForecast(
    val location: WeatherLocation,
    val currentWeather: CurrentWeather,
    val hourlyForecast: List<HourlyForecast>,
    val dailyForecast: List<DailyForecast>
)

sealed interface WeatherEvent {
    data class ShowError(val text: String) : WeatherEvent
}