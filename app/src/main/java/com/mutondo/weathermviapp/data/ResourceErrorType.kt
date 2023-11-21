package com.mutondo.weathermviapp.data

import androidx.annotation.StringRes
import com.mutondo.weathermviapp.R

enum class ResourceErrorType(@StringRes val message: Int) {
    NONE(R.string.blank),
    NETWORK(R.string.error_general_data_retrieve),
}