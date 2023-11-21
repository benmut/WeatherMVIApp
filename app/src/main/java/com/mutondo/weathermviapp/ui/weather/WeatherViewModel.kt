package com.mutondo.weathermviapp.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.mutondo.weathermviapp.data.ResourceErrorType
import com.mutondo.weathermviapp.data.ResourceStatus
import com.mutondo.weathermviapp.domain.repositories.WeatherRepository
import com.mutondo.weathermviapp.ui.mvibase.MviViewModel
import com.mutondo.weathermviapp.ui.weather.WeatherIntent.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel(), MviViewModel<WeatherIntent, WeatherViewState, WeatherResult> {

    lateinit var latitude: String
    lateinit var longitude: String

    private var currentViewState = WeatherViewState.default()

    private val mviIntent: MutableLiveData<WeatherIntent> by lazy {
        MutableLiveData()
    }

    val viewState: LiveData<WeatherViewState> = mviIntent.switchMap {
            weatherIntent -> processIntent(weatherIntent).map {
                reduce(currentViewState, it)
            }
    }

    override fun processIntent(intent: WeatherIntent): LiveData<WeatherResult> = liveData {
        when(intent) {
            is GetWeatherIntent -> {
                emit(WeatherResult.GetWeatherResult.Loading)
                emit(getWeather(intent.latitude, intent.longitude, intent.apiKey))
            }

            is GetForecastIntent -> {
                emit(WeatherResult.GetForecastResult.Loading)
                emit(getForecast(intent.latitude, intent.longitude, intent.apiKey))
            }
        }
    }

    override fun reduce(state: WeatherViewState, result: WeatherResult): WeatherViewState {
        when(result) {
            is WeatherResult.GetWeatherResult -> when (result) {
                is WeatherResult.GetWeatherResult.Loading -> {
                    currentViewState = state.copy(isLoading = true)
                    return currentViewState
                }

                is WeatherResult.GetWeatherResult.Success -> {
                    currentViewState = state.copy(
                        isLoading = false,
                        temperature = result.weather.temperature,
                        temperatureMax = result.weather.temperatureMax,
                        temperatureMin = result.weather.temperatureMin,
                        description = result.weather.main,
                    )
                    return currentViewState
                }

                is WeatherResult.GetWeatherResult.Failure -> {
                    currentViewState = state.copy(
                        isLoading = false,
                        error = result.error
                    )
                    return currentViewState
                }
            }

            is WeatherResult.GetForecastResult -> when(result) {
                is WeatherResult.GetForecastResult.Loading -> {
                    currentViewState = state.copy(isLoading = true)
                    return currentViewState
                }

                is WeatherResult.GetForecastResult.Success -> {
                    currentViewState = state.copy(
                        isLoading = false,
                        forecasts = result.forecasts
                    )
                    return currentViewState
                }

                is WeatherResult.GetForecastResult.Failure -> {
                    currentViewState = state.copy(
                        isLoading = false,
                        error = result.error
                    )
                    return currentViewState
                }
            }
        }
    }

    fun setMviIntent(intent: WeatherIntent) {
//        mviIntent.value = intent
        mviIntent.postValue(intent)
    }


    private suspend fun getWeather(latitude: String, longitude: String, appId: String): WeatherResult {
//        return  liveData {
            val resource = weatherRepository.getWeatherData(latitude, longitude, appId)

            return when (resource.status) {
                ResourceStatus.ERROR -> {
                    WeatherResult.GetWeatherResult.Failure(ResourceErrorType.NETWORK.toString())
                }

                ResourceStatus.SUCCESS -> {
                    WeatherResult.GetWeatherResult.Success(resource.data!!)
                }
            }
//        }
    }

    private suspend fun getForecast(latitude: String, longitude: String, appId: String): WeatherResult {
//        return  liveData {
            val resource = weatherRepository.getForecast5Data(latitude, longitude, appId)

            return when (resource.status) {
                ResourceStatus.ERROR -> {
                    WeatherResult.GetForecastResult.Failure(ResourceErrorType.NETWORK.toString())
                }

                ResourceStatus.SUCCESS -> {
                    WeatherResult.GetForecastResult.Success(resource.data!!)
                }
            }
//        }
    }

}