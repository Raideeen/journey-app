package com.example.journey.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.journey.model.Converters
import com.example.journey.model.CountryEntity

/**
 * Room database for storing country data.
 * This database uses the CountryDao to interact with the country_table in the database.
 * It also uses the Converters class to convert between different data types.
 *
 * @property countryDao The DAO used to interact with the country_table in the database.
 */
@Database(entities = [CountryEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        // Singleton instance of the CountryDatabase
        @Volatile
        private var INSTANCE: CountryDatabase? = null

        /**
         * Returns the singleton instance of the CountryDatabase.
         * If the instance is null, it creates a new instance and assigns it to INSTANCE.
         *
         * @param context The context used to create the database.
         * @return The singleton instance of the CountryDatabase.
         */
        fun getDatabase(context: Context): CountryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CountryDatabase::class.java,
                    "country_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}