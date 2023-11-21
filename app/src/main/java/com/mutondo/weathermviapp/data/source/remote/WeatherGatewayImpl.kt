package com.mutondo.weathermviapp.data.source.remote

import com.mutondo.weatherdvtapp.data.models.ForecastResponse
import com.mutondo.weathermviapp.data.apiservice.WeatherService
import com.mutondo.weathermviapp.data.models.WeatherResponse
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

interface WeatherGateway {
    suspend fun getWeatherData(latitude: String, longitude: String, apiKey: String): WeatherResponse?
    suspend fun getForecast5Data(latitude: String, longitude: String, apiKey: String): ForecastResponse?
}

@Singleton
class WeatherGatewayImpl @Inject constructor(
    retrofit: Retrofit
): WeatherGateway {

    private val service = retrofit.create(WeatherService::class.java)

    override suspend fun getWeatherData(latitude: String, longitude: String, apiKey: String)
    : WeatherResponse? {
        return service.getWeatherData(latitude, longitude, apiKey)
    }

    override suspend fun getForecast5Data(latitude: String, longitude: String, apiKey: String)
    : ForecastResponse? {
        return service.getForecast5Data(latitude, longitude, apiKey)
    }
}