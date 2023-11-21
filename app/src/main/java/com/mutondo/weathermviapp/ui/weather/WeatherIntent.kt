package com.mutondo.weathermviapp.ui.weather

import com.mutondo.weathermviapp.ui.mvibase.MviIntent

sealed class WeatherIntent : MviIntent {

    data class GetWeatherIntent(
        val latitude: String,
        val longitude: String,
        val apiKey: String
    ): WeatherIntent()

    data class GetForecastIntent(
        val latitude: String,
        val longitude: String,
        val apiKey: String
    ): WeatherIntent()
}
