package com.mutondo.weathermviapp.ui.favorite

import com.mutondo.weathermviapp.domain.models.FavoriteLocation
import com.mutondo.weathermviapp.ui.mvibase.MviResult

sealed class FavoriteLocationResult : MviResult {

    sealed class GetAllFavoritesResult : FavoriteLocationResult() {
        object Loading : GetAllFavoritesResult()
        data class Success(val favorites: List<FavoriteLocation>) : GetAllFavoritesResult()
        data class Failure(val error: String?) : GetAllFavoritesResult()
    }

    sealed class SaveFavoriteResult : FavoriteLocationResult() {
        data class Success(val rowId: Long) : SaveFavoriteResult()
////        data class Failure(val error: String?) : SaveFavoriteResult()
//        //TODO - check the possible Room error
    }
}