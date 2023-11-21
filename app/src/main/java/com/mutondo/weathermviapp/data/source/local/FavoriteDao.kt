package com.mutondo.weathermviapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mutondo.weathermviapp.data.source.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM ${FavoriteEntity.TABLE_NAME}")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM ${FavoriteEntity.TABLE_NAME} WHERE name = :name")
    fun getFavorite(name: String): Flow<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg favorites: FavoriteEntity): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorites: List<FavoriteEntity>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(favorite: FavoriteEntity): Int

    @Delete
    suspend fun delete(favorite: FavoriteEntity)

    @Query("DELETE FROM ${FavoriteEntity.TABLE_NAME}")
    suspend fun deleteAllFavorites()

}