package com.mikhaylenko.weatherdemo.data

import com.google.gson.JsonParseException
import com.mikhaylenko.weatherdemo.data.mapper.toDomain
import com.mikhaylenko.weatherdemo.domain.model.WeatherForecast
import com.mikhaylenko.weatherdemo.domain.network.WeatherRepository
import com.mikhaylenko.weatherdemo.domain.WeatherError
import com.mikhaylenko.weatherdemo.domain.WeatherResult
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

private const val DAYS_FORECAST = 3

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): WeatherResult<WeatherForecast> = try {
        val response = apiService.getWeatherForecast(
            query = "$latitude,$longitude",
            days = DAYS_FORECAST
        )
        WeatherResult.Success(response.toDomain())
    } catch (error: CancellationException) {
        throw error
    } catch (error: SocketTimeoutException) {
        WeatherResult.Failure(WeatherError.Timeout(error.message))
    } catch (error: UnknownHostException) {
        WeatherResult.Failure(WeatherError.NoInternet)
    } catch (error: ConnectException) {
        WeatherResult.Failure(WeatherError.NoInternet)
    } catch (error: HttpException) {
        WeatherResult.Failure(WeatherError.Http(error.code()))
    } catch (error: JsonParseException) {
        WeatherResult.Failure(WeatherError.InvalidResponse)
    } catch (error: IOException) {
        WeatherResult.Failure(WeatherError.NetworkFailure)
    } catch (error: Exception) {
        WeatherResult.Failure(WeatherError.Unknown)
    }
}
