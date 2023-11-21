package com.mutondo.weathermviapp.data.source.local.mappers

import com.mutondo.weathermviapp.data.source.local.entities.FavoriteEntity
import com.mutondo.weathermviapp.data.source.local.entities.LocationColumns
import com.mutondo.weathermviapp.utils.orZero
import com.mutondo.weathermviapp.domain.models.FavoriteLocation

object FavoriteLocationMapper {

    fun mapFrom(favoriteEntity: FavoriteEntity?): FavoriteLocation {
        return favoriteEntity.let {
            FavoriteLocation(
                name = it?.name.orEmpty(),
                latitude = it?.location?.latitude.orZero,
                longitude = it?.location?.longitude.orZero
            )
        }
    }

    fun mapTo(favorite: FavoriteLocation): FavoriteEntity {
        return FavoriteEntity(
            name = favorite.name,
            location = LocationColumns(favorite.latitude, favorite.longitude)
        )
    }

}

