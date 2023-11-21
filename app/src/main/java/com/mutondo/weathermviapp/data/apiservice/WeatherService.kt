package com.mutondo.weathermviapp.data.apiservice

import com.mutondo.weatherdvtapp.data.models.ForecastResponse
import com.mutondo.weathermviapp.data.models.WeatherResponse
import retrofit2.http.*

interface WeatherService {

    @GET("weather")
    suspend fun getWeatherData(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("apiKey") apiKey: String): WeatherResponse?

    @GET("forecast")
    suspend fun getForecast5Data(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("apiKey") apiKey: String): ForecastResponse?

}