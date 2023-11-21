package com.mutondo.weathermviapp.ui.weather

import com.mutondo.weathermviapp.domain.models.Forecast
import com.mutondo.weathermviapp.ui.mvibase.MviViewState

data class WeatherViewState(
    val isLoading: Boolean,
    val temperature: Float,
    val temperatureMax: Float,
    val temperatureMin: Float,
    val description: String,
    val error: String?,
    val forecasts: List<Forecast>
) : MviViewState {

    companion object {
        fun default(): WeatherViewState {
            return WeatherViewState(
                isLoading = false,
                temperature = 0f,
                temperatureMin = 0f,
                temperatureMax = 0f,
                description = String(),
                error = null,
                forecasts = emptyList()
            )
        }
    }
}
