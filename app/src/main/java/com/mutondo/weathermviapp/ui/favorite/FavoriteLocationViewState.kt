package com.mutondo.weathermviapp.ui.favorite

import com.mutondo.weathermviapp.domain.models.FavoriteLocation
import com.mutondo.weathermviapp.ui.mvibase.MviViewState

data class FavoriteLocationViewState(
    val isLoading: Boolean,
    val favorites: List<FavoriteLocation>,
    val rowId:Long

) : MviViewState {
    companion object {
        fun default(): FavoriteLocationViewState {
            return FavoriteLocationViewState(
                isLoading = false,
                favorites = emptyList(),
                rowId = 0L
            )
        }
    }
}