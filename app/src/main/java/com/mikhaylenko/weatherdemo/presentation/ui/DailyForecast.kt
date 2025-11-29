package com.mikhaylenko.weatherdemo.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mikhaylenko.weatherdemo.data.model.Forecastday
import com.mikhaylenko.weatherdemo.ui.theme.CardBackground
import com.mikhaylenko.weatherdemo.ui.theme.DarkText
import com.mikhaylenko.weatherdemo.ui.theme.LightText
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DailyForecastList(dailyForecast: List<Forecastday>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "3-Day Forecast", // A title for the daily forecast section
            style = MaterialTheme.typography.headlineSmall,
            color = DarkText,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp) // Smaller spacing for daily items
        ) {
            items(dailyForecast) { day ->
                DailyForecastItem(day = day)
            }
        }
    }
}

@Composable
fun DailyForecastItem(day: Forecastday) {
    val dateFormatter = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    val date = dateFormatter.format(Date(day.dateEpoch * 1000))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = date, style = MaterialTheme.typography.bodyLarge, color = DarkText)

            // Placeholder for Weather Icon
            Text(
                text = "⛅", // Placeholder icon, replace with actual weather icon
                style = MaterialTheme.typography.bodyLarge,
                color = DarkText,
                modifier = Modifier.weight(1f), // Take available space
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${day.day.maxtempC.toInt()}°", style = MaterialTheme.typography.bodyLarge, color = DarkText)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${day.day.mintempC.toInt()}°", style = MaterialTheme.typography.bodyLarge, color = LightText)
            }
        }
    }
}