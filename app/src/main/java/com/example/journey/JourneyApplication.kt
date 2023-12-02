package com.example.journey

import android.app.Application
import androidx.room.Room
import com.example.journey.service.StoryDatabase

class JourneyApplication : Application() {
    private lateinit var database: StoryDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, StoryDatabase::class.java, "story_database")
            .fallbackToDestructiveMigration() // Handle migrations
            .build()
    }
}