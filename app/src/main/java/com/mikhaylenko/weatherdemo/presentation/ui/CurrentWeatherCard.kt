package com.mikhaylenko.weatherdemo.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikhaylenko.weatherdemo.data.model.CurrentWeather
import com.mikhaylenko.weatherdemo.data.model.Location
import com.mikhaylenko.weatherdemo.ui.theme.CardBackground
import com.mikhaylenko.weatherdemo.ui.theme.DarkText
import com.mikhaylenko.weatherdemo.ui.theme.LightText
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CurrentWeatherCard(location: Location, currentWeather: CurrentWeather) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // No elevation for a flat look
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${location.name}, ${location.country}",
                style = MaterialTheme.typography.headlineSmall,
                color = DarkText
            )
            Text(
                text = "Now", // For current weather, always "Now" or adapt based on time
                style = MaterialTheme.typography.bodyMedium,
                color = LightText
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Placeholder for Weather Icon
            Text(
                text = "☀️", // Placeholder icon, replace with actual weather icon
                fontSize = 60.sp, // Adjust size as needed
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${currentWeather.tempC.toInt()}°", // Display as integer, matching reference
                style = MaterialTheme.typography.displayLarge,
                color = DarkText
            )
            Text(
                text = currentWeather.condition.text,
                style = MaterialTheme.typography.bodyLarge,
                color = DarkText
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Wind information
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "💨", // Placeholder for wind icon
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkText
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Wind ${currentWeather.windKph} km/h", // Assuming km/h is preferred
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkText
                )
            }
        }
    }
}