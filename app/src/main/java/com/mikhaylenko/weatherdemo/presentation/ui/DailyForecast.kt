package com.mikhaylenko.weatherdemo.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mikhaylenko.weatherdemo.data.model.Forecastday
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DailyForecastList(dailyForecast: List<Forecastday>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Прогноз на 3 дня", // A title for the daily forecast section
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp) // Smaller spacing for daily items
        ) {
            dailyForecast.forEach { day ->
                DailyForecastItem(day = day)
            }
        }
    }
}

@Composable
fun DailyForecastItem(day: Forecastday) {
    val dateFormatter = SimpleDateFormat("EEE, d MMM", Locale("ru"))
    val date = dateFormatter.format(Date(day.dateEpoch * 1000))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp) // Subtle elevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = date, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)

            // Weather Icon
            Icon(
                imageVector = Icons.Default.WbSunny, // Placeholder icon
                contentDescription = day.day.condition.text,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally) // Center the icon
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${day.day.maxtempC.toInt()}°", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${day.day.mintempC.toInt()}°", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}