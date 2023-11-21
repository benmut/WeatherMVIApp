package com.mutondo.weathermviapp.data.repositories

import com.mutondo.weathermviapp.data.Resource
import com.mutondo.weathermviapp.data.ResourceErrorType
import com.mutondo.weathermviapp.data.mappers.WeatherMapper
import com.mutondo.weathermviapp.data.source.remote.WeatherGateway
import com.mutondo.weathermviapp.domain.models.Forecast
import com.mutondo.weathermviapp.domain.models.Weather
import com.mutondo.weathermviapp.domain.repositories.WeatherRepository
import com.mutondo.weathermviapp.ui.weather.WeatherViewState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherGateway: WeatherGateway,
) : WeatherRepository {

    override suspend fun getWeatherData(latitude: String, longitude: String, apiKey: String)
    : Resource<Weather> {
        val response = weatherGateway.getWeatherData(latitude, longitude, apiKey)

        return if(response == null) {
            Resource.error(ResourceErrorType.NETWORK, null)
        } else {
            Resource.success(WeatherMapper.map(response))
        }
    }

    override suspend fun getForecast5Data(latitude: String, longitude: String, apiKey: String)
    : Resource<List<Forecast>> {
        val response = weatherGateway.getForecast5Data(latitude, longitude, apiKey)

        return if (response == null) {
            Resource.error(ResourceErrorType.NETWORK, null)
        } else {
            Resource.success(WeatherMapper.mapTemp(response))
        }
    }
}