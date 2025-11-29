package com.mikhaylenko.weatherdemo.data.model

import com.google.gson.annotations.SerializedName

// Главный объект
data class WeatherApiResponse(
    val location: Location,						// Информация о местоположении
    val current: CurrentWeather,				// Текущая погода
    val forecast: Forecast						// Прогноз погоды
)

// Информация о местоположении
data class Location(
    val name: String,							// Название города
    val region: String,							// Регион
    val country: String,						// Страна
    val lat: Double,							// Широта
    val lon: Double,							// Долгота
    val tz_id: String,							// Идентификатор временной зоны
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,					// Локальное время в формате Unix timestamp
    val localtime: String						// Локальное время в текстовом формате
)

// Текущая погода
data class CurrentWeather(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long,					// Время последнего обновления в формате Unix timestamp
    @SerializedName("last_updated")
    val lastUpdated: String,					// Время последнего обновления в текстовом формате
    @SerializedName("temp_c")
    val tempC: Double,							// Температура в градусах Цельсия
    @SerializedName("temp_f")
    val tempF: Double,							// Температура в градусах Фаренгейта
    @SerializedName("is_day")
    val isDay: Int,								// 1 - день, 0 - ночь
    val condition: Condition,					// Состояние погоды
    @SerializedName("wind_mph")
    val windMph: Double,						// Скорость ветра в милях в час
    @SerializedName("wind_kph")
    val windKph: Double,						// Скорость ветра в километрах в час
    @SerializedName("wind_degree")
    val windDegree: Int,						// Направление ветра в градусах
    @SerializedName("wind_dir")
    val windDir: String,						// Направление ветра (аббревиатура)
    @SerializedName("pressure_mb")
    val pressureMb: Double,						// Давление в миллибарах
    @SerializedName("pressure_in")
    val pressureIn: Double,						// Давление в дюймах ртутного столба
    @SerializedName("precip_mm")
    val precipMm: Double,						// Количество осадков в миллиметрах
    @SerializedName("precip_in")
    val precipIn: Double,						// Количество осадков в дюймах
    val humidity: Int,							// Влажность в процентах
    val cloud: Int,								// Облачность в процентах
    @SerializedName("feelslike_c")
    val feelslikeC: Double,						// Ощущается как, в градусах Цельсия
    @SerializedName("feelslike_f")
    val feelslikeF: Double,						// Ощущается как, в градусах Фаренгейта
    @SerializedName("vis_km")
    val visKm: Double,							// Видимость в километрах
    @SerializedName("vis_miles")
    val visMiles: Double,						// Видимость в милях
    val uv: Double,								// УФ-индекс
    @SerializedName("gust_mph")
    val gustMph: Double,						// Порывы ветра в милях в час
    @SerializedName("gust_kph")
    val gustKph: Double							// Порывы ветра в километрах в час
)

// Детали состояния погоды
data class Condition(
    val text: String,							// Текстовое описание состояния погоды
    val icon: String,							// URL иконки погоды
    val code: Int								// Код состояния погоды
)

// Прогноз погоды
data class Forecast(
    val forecastday: List<Forecastday>			// Список дней с прогнозом
)

// Прогноз на конкретный день
data class Forecastday(
    val date: String,							// Дата прогноза
    @SerializedName("date_epoch")
    val dateEpoch: Long,						// Дата в формате Unix timestamp
    val day: Day,								// Дневной прогноз
    val astro: Astro,							// Астрономические данные
    val hour: List<Hour>						// Почасовой прогноз
)

// Ежедневный прогноз
data class Day(
    @SerializedName("maxtemp_c")
    val maxtempC: Double,						// Максимальная температура за день в градусах Цельсия
    @SerializedName("maxtemp_f")
    val maxtempF: Double,						// Максимальная температура за день в градусах Фаренгейта
    @SerializedName("mintemp_c")
    val mintempC: Double,						// Минимальная температура за день в градусах Цельсия
    @SerializedName("mintemp_f")
    val mintempF: Double,						// Минимальная температура за день в градусах Фаренгейта
    @SerializedName("avgtemp_c")
    val avgtempC: Double,						// Средняя температура за день в градусах Цельсия
    @SerializedName("avgtemp_f")
    val avgtempF: Double,						// Средняя температура за день в градусах Фаренгейта
    @SerializedName("maxwind_mph")
    val maxwindMph: Double,						// Максимальная скорость ветра в милях в час
    @SerializedName("maxwind_kph")
    val maxwindKph: Double,						// Максимальная скорость ветра в километрах в час
    @SerializedName("totalprecip_mm")
    val totalprecipMm: Double,					// Общее количество осадков в миллиметрах
    @SerializedName("totalprecip_in")
    val totalprecipIn: Double,					// Общее количество осадков в дюймах
    @SerializedName("totalsnow_cm")
    val totalsnowCm: Double,					// Общее количество снега в сантиметрах
    @SerializedName("avgvis_km")
    val avgvisKm: Double,						// Средняя видимость в километрах
    @SerializedName("avgvis_miles")
    val avgvisMiles: Double,					// Средняя видимость в милях
    val avghumidity: Int,						// Средняя влажность в процентах
    @SerializedName("daily_will_it_rain")
    val dailyWillItRain: Int,					// Будет ли дождь: 1 - да, 0 - нет
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int,					// Вероятность дождя в процентах
    @SerializedName("daily_will_it_snow")
    val dailyWillItSnow: Int,					// Будет ли снег: 1 - да, 0 - нет
    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int,					// Вероятность снега в процентах
    val condition: Condition,					// Состояние погоды
    val uv: Double								// УФ-индекс
)

// Астрономические данные
data class Astro(
    val sunrise: String,						// Время восхода солнца
    val sunset: String,							// Время заката
    val moonrise: String,						// Время восхода луны
    val moonset: String,						// Время захода луны
    @SerializedName("moon_phase")
    val moonPhase: String,						// Фаза луны
    @SerializedName("moon_illumination")
    val moonIllumination: String				// Освещенность луны
)

// Почасовой прогноз
data class Hour(
    @SerializedName("time_epoch")
    val timeEpoch: Long,						// Время в формате Unix timestamp
    val time: String,							// Время в текстовом формате
    @SerializedName("temp_c")
    val tempC: Double,							// Температура в градусах Цельсия
    @SerializedName("temp_f")
    val tempF: Double,							// Температура в градусах Фаренгейта
    @SerializedName("is_day")
    val isDay: Int,								// 1 - день, 0 - ночь
    val condition: Condition,					// Состояние погоды
    @SerializedName("wind_mph")
    val windMph: Double,						// Скорость ветра в милях в час
    @SerializedName("wind_kph")
    val windKph: Double,						// Скорость ветра в километрах в час
    @SerializedName("wind_degree")
    val windDegree: Int,						// Направление ветра в градусах
    @SerializedName("wind_dir")
    val windDir: String,						// Направление ветра (аббревиатура)
    @SerializedName("pressure_mb")
    val pressureMb: Double,						// Давление в миллибарах
    @SerializedName("pressure_in")
    val pressureIn: Double,						// Давление в дюймах ртутного столба
    @SerializedName("precip_mm")
    val precipMm: Double,						// Количество осадков в миллиметрах
    @SerializedName("precip_in")
    val precipIn: Double,						// Количество осадков в дюймах
    val humidity: Int,							// Влажность в процентах
    val cloud: Int,								// Облачность в процентах
    @SerializedName("feelslike_c")
    val feelslikeC: Double,						// Ощущается как, в градусах Целься
    @SerializedName("feelslike_f")
    val feelslikeF: Double,						// Ощущается как, в градусах Фаренгейта
    @SerializedName("windchill_c")
    val windchillC: Double,						// Охлаждение ветром, в градусах Цельсия
    @SerializedName("windchill_f")
    val windchillF: Double,						// Охлаждение ветром, в градусах Фаренгейта
    @SerializedName("heatindex_c")
    val heatindexC: Double,						// Тепловой индекс, в градусах Цельсия
    @SerializedName("heatindex_f")
    val heatindexF: Double,						// Тепловой индекс, в градусах Фаренгейта
    @SerializedName("dewpoint_c")
    val dewpointC: Double,						// Точка росы, в градусах Цельсия
    @SerializedName("dewpoint_f")
    val dewpointF: Double,						// Точка росы, в градусах Фаренгейта
    @SerializedName("will_it_rain")
    val willItRain: Int,						// Будет ли дождь: 1 - да, 0 - нет
    @SerializedName("chance_of_rain")
    val chanceOfRain: Int,						// Вероятность дождя в процентах
    @SerializedName("will_it_snow")
    val willItSnow: Int,						// Будет ли снег: 1 - да, 0 - нет
    @SerializedName("chance_of_snow")
    val chanceOfSnow: Int,						// Вероятность снега в процентах
    @SerializedName("vis_km")
    val visKm: Double,							// Видимость в километрах
    @SerializedName("vis_miles")
    val visMiles: Double,						// Видимость в милях
    @SerializedName("gust_mph")
    val gustMph: Double,						// Порывы ветра в милях в час
    @SerializedName("gust_kph")
    val gustKph: Double,						// Порывы ветра в километрах в час
    val uv: Double								// УФ-индекс
)