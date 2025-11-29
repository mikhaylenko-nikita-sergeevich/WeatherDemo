package com.mikhaylenko.weatherdemo.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mikhaylenko.weatherdemo.data.model.Hour
import com.mikhaylenko.weatherdemo.ui.theme.CardBackground
import com.mikhaylenko.weatherdemo.ui.theme.DarkText
import com.mikhaylenko.weatherdemo.ui.theme.LightText
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HourlyForecastRow(hourlyForecast: List<Hour>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Hourly Forecast",
            style = MaterialTheme.typography.headlineSmall,
            color = DarkText,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(hourlyForecast) { hour ->
                HourlyForecastItem(hour = hour)
            }
        }
    }
}

@Composable
fun HourlyForecastItem(hour: Hour) {
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val time = timeFormatter.format(Date(hour.timeEpoch * 1000))

    Card(
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = time, style = MaterialTheme.typography.bodyMedium, color = DarkText)
            Spacer(modifier = Modifier.height(8.dp))
            // Placeholder for Weather Icon
            Text(
                text = "☁️", // Placeholder icon, replace with actual weather icon
                style = MaterialTheme.typography.bodyLarge,
                color = DarkText
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${hour.tempC.toInt()}°", style = MaterialTheme.typography.bodyLarge, color = DarkText)
        }
    }
}