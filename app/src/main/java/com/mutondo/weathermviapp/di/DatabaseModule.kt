package com.mutondo.weathermviapp.di

import android.content.Context
import com.mutondo.weathermviapp.data.source.local.FavoriteDao
import com.mutondo.weatherdvtapp.data.source.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): FavoriteDao {
        return WeatherDatabase.getInstance(appContext).favorites()
    }
}