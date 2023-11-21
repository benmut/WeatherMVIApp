package com.mutondo.weathermviapp.ui.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mutondo.weathermviapp.R
import com.mutondo.weathermviapp.databinding.ItemWeatherBinding
import com.mutondo.weathermviapp.domain.models.Forecast
import com.mutondo.weathermviapp.utils.AppUtils.Companion.convertKelvinToCelsius
import com.mutondo.weathermviapp.utils.DayTimeUtils.Companion.getDayOfWeek

class WeatherAdapter(
    private val context: Context,
    private val forecasts: List<Forecast>,
    private val weatherDescription: String
): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherViewHolder {
        return WeatherViewHolder(
            ItemWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    inner class WeatherViewHolder(private val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: Forecast) {
            binding.day.text = getDayOfWeek(forecast.timeStampL)
            binding.dayTemp.text = convertKelvinToCelsius(forecast.temperature)
            binding.icon.setImageResource(setForecastIcon(forecast.description))

//            setItemBackground(weatherDescription)
        }

        private fun setItemBackground(description: String) {
            if(description.contains("sun", true)) {
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.blue_dvt))
            } else if(description.contains("rain", true)) {
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.rainy))
            } else if(description.contains("cloud", true)) {
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.cloudy))
            } else {
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.blue_dvt))
            }
        }

        private fun setForecastIcon(description: String): Int {
            return if(description.contains("sun", true)) {
                R.drawable.ic_partly_sunny
            } else if(description.contains("clear", true)) {
                R.drawable.ic_clear
            } else if(description.contains("rain", true)) {
                R.drawable.ic_rain
            } else if(description.contains("cloud", true)) {
                R.drawable.ic_cloud // icon added for cloud
            } else {
                R.drawable.ic_dash
            }
        }
    }
}