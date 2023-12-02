package com.example.journey

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.journey.model.StoryEntity
import com.example.journey.model.StoryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [StoryEntity::class], version = 1)
abstract class StoryDataBase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StoryDataBase? = null

        private class StoryDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        var storyDao = database.storyDao()

                        // Delete all content here.
                        storyDao.deleteAll()

                        // Add sample words.
                        var story = StoryEntity(
                            id = 1,
                            title = "Hello",
                            subTitle = "World!",
                            imageResourceId = R.drawable.img_wall_of_china,
                            storyImageBanner = R.drawable.img_wall_of_china_selfie,
                            storyDetails = "This is a story about a place called China"
                        )
                        storyDao.upinsert(story)
                    }
                }
            }
        }

        fun getDataBase(context: Context): StoryDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoryDataBase::class.java,
                    "story_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}