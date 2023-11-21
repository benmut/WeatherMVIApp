package com.mutondo.weathermviapp.ui.weather

import com.mutondo.weathermviapp.domain.models.Forecast
import com.mutondo.weathermviapp.domain.models.Weather
import com.mutondo.weathermviapp.ui.mvibase.MviResult

sealed class WeatherResult : MviResult {

    sealed class GetWeatherResult : WeatherResult() {
        object Loading : GetWeatherResult()
        data class Success(val weather: Weather) : GetWeatherResult()
        data class Failure(val error: String?) : GetWeatherResult()
    }

    sealed class GetForecastResult : WeatherResult() {
        object Loading : GetForecastResult()
        data class Success(val forecasts: List<Forecast>) : GetForecastResult()
        data class Failure(val error: String?) : GetForecastResult()
    }
}