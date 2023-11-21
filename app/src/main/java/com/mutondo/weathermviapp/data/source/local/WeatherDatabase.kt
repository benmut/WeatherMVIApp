package com.mutondo.weatherdvtapp.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mutondo.weathermviapp.data.source.local.entities.FavoriteEntity
import com.mutondo.weatherdvtapp.data.source.local.migrations.MIGRATION_1_2
import com.mutondo.weathermviapp.data.source.local.FavoriteDao

@Database(entities = [FavoriteEntity::class], version = 2)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun favorites(): FavoriteDao

    companion object {

        @Volatile
        private var instance: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WeatherDatabase {
            val dbBuilder = Room.databaseBuilder(
                context.applicationContext, WeatherDatabase::class.java, DATABASE_NAME
            )
                .addMigrations(MIGRATION_1_2)

            return dbBuilder.build()
        }

        private const val DATABASE_NAME = "WeatherDatabase"
    }
}

//@VisibleForTesting
//internal val MIGRATION_1_2 = object : Migration(1, 2) {
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("")
//    }
//}