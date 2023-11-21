package com.mutondo.weathermviapp.data.mappers

import com.mutondo.weatherdvtapp.data.models.ForecastResponse
import com.mutondo.weathermviapp.data.models.WeatherResponse
import com.mutondo.weathermviapp.domain.models.Forecast
import com.mutondo.weathermviapp.domain.models.Weather
import com.mutondo.weathermviapp.utils.orZero

object WeatherMapper {

    fun map(response: WeatherResponse?): Weather {
        return response.let {
            Weather(
                longitude = it?.coordinate?.longitude.orZero,
                latitude = it?.coordinate?.latitude.orZero,
                main = it?.weathers?.get(0)?.main.orEmpty(),
                temperature = it?.main?.temperature.orZero,
                temperatureMin = it?.main?.temperatureMin.orZero,
                temperatureMax = it?.main?.temperatureMax.orZero
            )
        }
    }

    fun mapTemp(response: ForecastResponse?): List<Forecast> {
        return response?.temperatures?.filter { t ->
            t.timeStampS!!.contains("12:00") }?.map {
            Forecast(
                temperature = it.main?.temperature.orZero,
                timeStampL = it.timeStampL.orZero,
                description = it.weathers?.get(0)?.main.orEmpty(),
                timeStampS = it.timeStampS.toString(),
            )
        } ?: listOf()
    }
}