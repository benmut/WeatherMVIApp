package com.mutondo.weathermviapp.domain.models

data class Weather(
    val longitude: Float,
    val latitude: Float,
    val main: String,
    val temperature: Float,
    val temperatureMax: Float,
    val temperatureMin: Float
)

data class Forecast(
    val temperature: Float,
    val timeStampL: Long,
    val description: String,
    val timeStampS: String,
)