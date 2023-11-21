package com.mutondo.weathermviapp.ui.favorite

import com.mutondo.weathermviapp.domain.models.FavoriteLocation
import com.mutondo.weathermviapp.ui.mvibase.MviIntent

sealed class FavoriteLocationIntent : MviIntent {

    object GetAllFavoritesIntent : FavoriteLocationIntent()

    data class SaveFavoriteIntent(
        val favorite: FavoriteLocation
    ) : FavoriteLocationIntent()
}