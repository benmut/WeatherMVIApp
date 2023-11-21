package com.mutondo.weatherdvtapp.data.models

import com.google.gson.annotations.SerializedName
import com.mutondo.weathermviapp.data.models.Main
import com.mutondo.weathermviapp.data.models.Weather

data class ForecastResponse(
    @SerializedName("list")
    val temperatures: List<Forecast>? = listOf()
)

data class Forecast(
    @SerializedName("dt_txt")
    val timeStampS: String? = null,
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("weather")
    val weathers: List<Weather>? = null,
    @SerializedName("dt")
    val timeStampL: Long? = null,
)
