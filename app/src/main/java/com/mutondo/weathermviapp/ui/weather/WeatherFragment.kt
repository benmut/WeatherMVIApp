package com.mutondo.weathermviapp.ui.weather

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutondo.weathermviapp.LocationService
import com.mutondo.weathermviapp.databinding.FragmentWeatherBinding
import com.mutondo.weathermviapp.domain.models.Forecast
import com.mutondo.weathermviapp.ui.main.BaseFragment
import com.mutondo.weathermviapp.ui.mvibase.MviView
import com.mutondo.weathermviapp.utils.AppUtils
import com.mutondo.weathermviapp.utils.Constants
import com.mutondo.weathermviapp.ui.weather.WeatherIntent.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule

@AndroidEntryPoint
class WeatherFragment : BaseFragment(), MviView<WeatherViewState> {

    private var binding: FragmentWeatherBinding? = null
    private lateinit var receiver: BroadcastReceiver

    @Inject
    lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBarTitle(String())
        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()

        val filter = IntentFilter(activity?.packageName.toString() + "MY_LOCATION")
        receiver = MyBroadcastReceiver()
        activity?.registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()
        context?.unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun subscribeObservers() {
        weatherViewModel.viewState.observe(viewLifecycleOwner, this::render)
    }

    override fun render(state: WeatherViewState) {
        binding!!.progress.visibility = if(state.isLoading) View.VISIBLE else View.INVISIBLE

        if(state.error != null) {
            showErrorMessage()
            return
        }

        with(binding!!) {
            currentWeatherCard.visibility = View.VISIBLE
            weatherDescription.text = state.description
            minTemp.text = AppUtils.convertKelvinToCelsius(state.temperatureMin)
            maxTemp.text = AppUtils.convertKelvinToCelsius(state.temperatureMax)
            currentTemp1.text = AppUtils.convertKelvinToCelsius(state.temperature)
            currentTemp2.text = AppUtils.convertKelvinToCelsius(state.temperature)
        }

        if(state.forecasts.isEmpty()) {
            showNoForecast()
        } else {
            updateForecastView(state.forecasts, state.description)
        }
    }

    private fun updateForecastView(forecasts: List<Forecast>, weatherDes: String) {
        with(binding!!) {
            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.hasFixedSize()
            recyclerview.adapter = WeatherAdapter(requireContext(), forecasts, weatherDes)
        }
    }

    private fun showErrorMessage() {
//         TODO - display error message to the User
    }

    private fun showNoForecast() {
//        TODO("Not yet implemented")
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if(intent?.action == activity?.packageName.toString() + "MY_LOCATION") {

                with(weatherViewModel) {
                    latitude = intent.extras?.getDouble(LocationService.LAT_KEY, 0.0).toString()
                    longitude = intent.extras?.getDouble(LocationService.LNG_KEY, 0.0).toString()
                }

                triggerGetWeatherIntent()
                //TODO - Not happy with this hack - Maybe use MediatorLiveData -  to be discussed
                Timer().schedule(1000) {
                    triggerGetForecastIntent()
                }
            }
        }
    }

    private fun triggerGetWeatherIntent() {
        with(weatherViewModel) {
            setMviIntent(GetWeatherIntent(latitude, longitude, Constants.API_KEY))
        }
    }

    private fun triggerGetForecastIntent() {
        with(weatherViewModel) {
            setMviIntent(GetForecastIntent(latitude, longitude, Constants.API_KEY))
        }
    }

    companion object {
        const val TAG = "WeatherFragment"
    }
}