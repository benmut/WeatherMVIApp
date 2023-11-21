package com.mutondo.weathermviapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

class DayTimeUtils {
    companion object {

        fun getDayOfWeek(timestamp: Long): String {
            return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
        }
    }
}