package com.mutondo.weathermviapp.domain.repositories

import com.mutondo.weathermviapp.domain.models.FavoriteLocation
import kotlinx.coroutines.flow.Flow

interface FavoriteLocationRepository {
    fun getAllFavorites(): Flow<List<FavoriteLocation>>
    fun getFavorite(name: String): Flow<FavoriteLocation>
    suspend fun insert(favorite: FavoriteLocation): Long

    suspend fun update(favorite: FavoriteLocation): Int
//    suspend fun delete(favorite: FavoriteLocation)
//    suspend fun deleteAllFavorites()
}