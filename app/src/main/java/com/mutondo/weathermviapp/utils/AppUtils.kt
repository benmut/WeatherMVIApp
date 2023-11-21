package com.mutondo.weathermviapp.utils

import android.location.LocationManager
import kotlin.math.roundToInt

class AppUtils {
    companion object {

        fun convertKelvinToCelsius(kelvin: Float): String {
            return (kelvin - 273.15f).roundToInt().toString() + "\u00B0"
        }

        fun isGpsEnabled(locationManager: LocationManager): Boolean {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        fun isNetworkEnabled(locationManager: LocationManager): Boolean {
            return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }
    }
}