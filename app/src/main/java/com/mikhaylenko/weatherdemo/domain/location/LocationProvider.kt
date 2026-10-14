package com.mikhaylenko.weatherdemo.domain.location

interface LocationProvider {
    suspend fun getCurrentLocation(): Coordinates
}

class LocationPermissionDeniedException(cause: Throwable? = null) :
    SecurityException("Location permission denied", cause)

class LocationProviderException(val statusCode: Int, cause: Throwable) :
    Exception("Location provider failed with status code $statusCode", cause)

class LocationUnavailableException : IllegalStateException()