package com.mutondo.weathermviapp.domain.repositories

import com.mutondo.weathermviapp.data.Resource
import com.mutondo.weathermviapp.domain.models.Forecast
import com.mutondo.weathermviapp.domain.models.Weather

interface WeatherRepository {
    suspend fun getWeatherData(latitude: String, longitude: String, apiKey: String): Resource<Weather>
    suspend fun getForecast5Data(latitude: String, longitude: String, apiKey: String): Resource<List<Forecast>>
}