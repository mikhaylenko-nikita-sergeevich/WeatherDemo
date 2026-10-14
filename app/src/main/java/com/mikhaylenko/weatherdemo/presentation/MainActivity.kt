package com.mikhaylenko.weatherdemo.presentation

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mikhaylenko.weatherdemo.presentation.theme.WeatherDemoTheme
import com.mikhaylenko.weatherdemo.presentation.ui.ErrorScreen
import com.mikhaylenko.weatherdemo.presentation.ui.LoadingScreen
import com.mikhaylenko.weatherdemo.presentation.ui.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private val locationPermission = Manifest.permission.ACCESS_COARSE_LOCATION
    private val permissionLauncher = getPermissionLauncher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherDemoTheme {
                val state by viewModel.weatherState.collectAsStateWithLifecycle()

                MainScreen(
                    state = state,
                    onRefresh = viewModel::onRefresh,
                    onRetry = viewModel::onRetry,
                    onRequestPermission = ::onRequestPermission
                )
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is WeatherEvent.ShowError -> showErrorToast(event)
                    }
                }
            }
        }
    }

    @Composable
    fun MainScreen(
        state: WeatherState,
        onRefresh: () -> Unit,
        onRetry: () -> Unit,
        onRequestPermission: () -> Unit
    ) {
        Log.d("!!!", "MainScreen(${state.javaClass.simpleName})")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (state) {
                is WeatherState.Loading -> LoadingScreen()
                is WeatherState.Content -> WeatherScreen(state, onRefresh)
                is WeatherState.Error -> ErrorScreen(state, onRetry, onRequestPermission)
            }
        }
    }

    private fun showErrorToast(error: WeatherEvent.ShowError) {
        Toast.makeText(
            this@MainActivity,
            error.text,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getPermissionLauncher() = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onLocationAccessGranted()
        } else {
            viewModel.onPermissionDenied()
        }
    }

    private fun onRequestPermission() {
        permissionLauncher.launch(locationPermission)
    }
}