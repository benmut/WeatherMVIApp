package com.mutondo.weathermviapp.di

import com.mutondo.weathermviapp.data.repositories.FavoriteLocationRepositoryImpl
import com.mutondo.weathermviapp.data.repositories.WeatherRepositoryImpl
import com.mutondo.weathermviapp.data.source.remote.WeatherGateway
import com.mutondo.weathermviapp.data.source.remote.WeatherGatewayImpl
import com.mutondo.weathermviapp.domain.repositories.FavoriteLocationRepository
import com.mutondo.weathermviapp.domain.repositories.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindWeatherRepository(implementation: WeatherRepositoryImpl): WeatherRepository

    @Singleton
    @Binds
    abstract fun bindWeatherGateway(implementation: WeatherGatewayImpl): WeatherGateway

    @Singleton
    @Binds
    abstract fun bindFavoriteLocationRepository(implementation: FavoriteLocationRepositoryImpl): FavoriteLocationRepository


}