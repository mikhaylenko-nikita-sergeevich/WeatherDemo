package com.mikhaylenko.weatherdemo.domain

import com.mikhaylenko.weatherdemo.domain.location.Coordinates
import com.mikhaylenko.weatherdemo.domain.location.LocationPermissionDeniedException
import com.mikhaylenko.weatherdemo.domain.location.LocationProvider
import com.mikhaylenko.weatherdemo.domain.location.LocationProviderException
import com.mikhaylenko.weatherdemo.domain.location.LocationUnavailableException
import com.mikhaylenko.weatherdemo.domain.model.WeatherForecast
import com.mikhaylenko.weatherdemo.domain.network.WeatherRepository
import javax.inject.Inject

private const val DEFAULT_LATITUDE = 55.7558
private const val DEFAULT_LONGITUDE = 37.6173

class GetWeatherForLocationUseCase @Inject constructor(
    private val locationProvider: LocationProvider,
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(useDefaultCity: Boolean): WeatherResult<WeatherForecast> = try {
        val location = when (useDefaultCity) {
            true -> Coordinates(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
            false -> locationProvider.getCurrentLocation()
        }

        weatherRepository.getWeatherForecast(
            latitude = location.latitude,
            longitude = location.longitude
        )
    } catch (error: LocationPermissionDeniedException) {
        WeatherResult.Failure(WeatherError.NoPermission)
    } catch (error: LocationUnavailableException) {
        WeatherResult.Failure(WeatherError.LocationUnavailable)
    } catch (error: LocationProviderException) {
        WeatherResult.Failure(WeatherError.LocationUnavailable)
    }

}
