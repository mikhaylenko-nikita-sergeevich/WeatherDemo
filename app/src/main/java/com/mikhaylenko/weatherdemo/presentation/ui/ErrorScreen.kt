package com.mikhaylenko.weatherdemo.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mikhaylenko.weatherdemo.R
import com.mikhaylenko.weatherdemo.domain.WeatherError
import com.mikhaylenko.weatherdemo.presentation.WeatherState

@Composable
fun ErrorScreen(
    state: WeatherState.Error,
    onRetry: () -> Unit,
    onRequestPermission: () -> Unit
) {
    LaunchedEffect(state) {
        Log.d("!!!", "Error: ${state.error}")//todo
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state.error) {
                is WeatherError.NoPermission -> PermissionError(onRequestPermission)
                else -> CommonError(
                    stringResource(R.string.error_title),
                    getErrorText(state.error),
                    stringResource(R.string.retry_btn),
                    onRetry
                )
            }
        }
    }
}

@Composable
private fun CommonError(
    title: String,
    message: String,
    button: String,
    onClick: () -> Unit
){
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = message,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = onClick) {
        Text(text = button)
    }
}

@Composable
private fun PermissionError(
    onRequestPermission: () -> Unit
){
    Text(
        text = stringResource(R.string.location_permission_denied_title),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(R.string.location_permission_denied_message),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = onRequestPermission) {
        Text(text = stringResource(R.string.location_permission_denied_button))
    }
}

@Composable
private fun getErrorText(error: WeatherError) = when (error) {
    is WeatherError.NoPermission -> stringResource(R.string.location_permission_denied)
    is WeatherError.LocationUnavailable -> stringResource(R.string.location_unavailable_error)
    is WeatherError.Timeout -> stringResource(R.string.timeout_error)
    is WeatherError.NoInternet -> stringResource(R.string.no_internet_error)
    is WeatherError.NetworkFailure -> stringResource(R.string.network_error)
    is WeatherError.InvalidResponse -> stringResource(R.string.invalid_response_error)
    is WeatherError.Http -> "http error; code ${error.statusCode}"
    is WeatherError.Unknown -> stringResource(R.string.unknown_error)
}