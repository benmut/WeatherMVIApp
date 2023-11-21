package com.mutondo.weathermviapp.data.repositories

import android.database.sqlite.SQLiteException
import com.mutondo.weathermviapp.data.source.local.FavoriteDao
import com.mutondo.weathermviapp.data.source.local.mappers.FavoriteLocationMapper
import com.mutondo.weathermviapp.domain.models.FavoriteLocation
import com.mutondo.weathermviapp.domain.repositories.FavoriteLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteLocationRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao

) : FavoriteLocationRepository {
    override fun getAllFavorites(): Flow<List<FavoriteLocation>> {
        return favoriteDao.getAllFavorites()
            .map { favoriteEntities ->
                val favorites = mutableListOf<FavoriteLocation>()
                for (favorite in favoriteEntities) {
                    favorites.add(FavoriteLocationMapper.mapFrom(favorite))
                }

                favorites
            }
    }

    override fun getFavorite(name: String): Flow<FavoriteLocation> {
        return favoriteDao.getFavorite(name)
            .map {
                FavoriteLocationMapper.mapFrom(it)
            }
    }

    override suspend fun insert(favorite: FavoriteLocation): Long {
        return favoriteDao.insert(
            FavoriteLocationMapper.mapTo(favorite)
        )
    }

    override suspend fun update(favorite: FavoriteLocation): Int {
        return favoriteDao.update(
            FavoriteLocationMapper.mapTo(favorite)
        )
    }

//    override suspend fun delete(favorite: FavoriteLocation) {
//        return delete(favorite)
//    }
//
//    override suspend fun deleteAllFavorites() {
//        return favoriteDao.deleteAllFavorites()
//    }
}