package com.mikhaylenko.weatherdemo.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.mikhaylenko.weatherdemo.domain.location.Coordinates
import com.mikhaylenko.weatherdemo.domain.location.LocationPermissionDeniedException
import com.mikhaylenko.weatherdemo.domain.location.LocationProvider
import com.mikhaylenko.weatherdemo.domain.location.LocationProviderException
import com.mikhaylenko.weatherdemo.domain.location.LocationUnavailableException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val LOCATION_AGE_MILLIS = 60_000L
private const val LOCATION_TIMEOUT_MILLIS = 30_000L

class LocationProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationProvider {

    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresPermission("android.permission.ACCESS_COARSE_LOCATION")
    override suspend fun getCurrentLocation(): Coordinates {
        if (!isLocationPermissionGranted(context)) throw LocationPermissionDeniedException()

        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        val cancellationTokenSource = CancellationTokenSource()

        val request = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setGranularity(Granularity.GRANULARITY_COARSE)
            .setMaxUpdateAgeMillis(LOCATION_AGE_MILLIS)
            .setDurationMillis(LOCATION_TIMEOUT_MILLIS)
            .build()

        val location = try {
            locationClient
                .getCurrentLocation(request, cancellationTokenSource.token)
                .await(cancellationTokenSource)
        } catch (error: SecurityException) {
            throw LocationPermissionDeniedException(error)
        } catch (error: ApiException) {
            throw LocationProviderException(error.statusCode, error)
        } ?: throw LocationUnavailableException()

        return Coordinates(
            latitude = location.latitude,
            longitude = location.longitude
        )
    }

    private fun isLocationPermissionGranted(context: Context): Boolean {
        val permission = Manifest.permission.ACCESS_COARSE_LOCATION
        val status = ContextCompat.checkSelfPermission(context, permission)
        return status == PackageManager.PERMISSION_GRANTED
    }
}
