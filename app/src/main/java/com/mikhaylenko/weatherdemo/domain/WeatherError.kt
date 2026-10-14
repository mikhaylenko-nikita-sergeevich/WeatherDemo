package com.mikhaylenko.weatherdemo.domain

sealed interface WeatherResult<out T> {
    data class Success<T>(val data: T) : WeatherResult<T>
    data class Failure(val error: WeatherError) : WeatherResult<Nothing>
}

sealed interface WeatherError{
    // Location
    data object NoPermission : WeatherError
    data object LocationUnavailable : WeatherError

    // Network
    data class Timeout(val message: String?) : WeatherError
    data object NoInternet : WeatherError
    data object NetworkFailure : WeatherError
    data object InvalidResponse : WeatherError
    data class Http(val statusCode: Int) : WeatherError
    data object Unknown : WeatherError
}