package com.example.journey.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.journey.model.StoryEntity

/**
 * Room database for storing story data.
 * This database uses the StoryDao to interact with the story_table in the database.
 *
 * @property storyDao The DAO used to interact with the story_table in the database.
 */
@Database(entities = [StoryEntity::class], version = 1, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao

    companion object {
        // Singleton instance of the StoryDatabase
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        /**
         * Returns the DAO for the StoryDatabase.
         * If the DAO is null, it creates a new DAO and assigns it to INSTANCE.
         *
         * @param context The context used to create the database.
         * @return The DAO for the StoryDatabase.
         */
        fun getDao(context: Context): StoryDao {
            return INSTANCE?.storyDao() ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java,
                    "story_database"
                ).build()

                INSTANCE = instance
                instance.storyDao()
            }
        }
    }
}