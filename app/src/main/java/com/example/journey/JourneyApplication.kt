package com.example.journey

import android.app.Application
import androidx.room.Room
import com.example.journey.model.Converters
import com.example.journey.service.CountryDatabase
import com.example.journey.service.StoryDatabase

class JourneyApplication : Application() {
    private lateinit var storyDatabase: StoryDatabase
    private lateinit var countryDatabase: CountryDatabase

    override fun onCreate() {
        super.onCreate()
        storyDatabase = Room.databaseBuilder(this, StoryDatabase::class.java, "story_database")
            .fallbackToDestructiveMigration() // Handle migrations
            .build()

        countryDatabase = Room.databaseBuilder(this, CountryDatabase::class.java, "country_database")
            .fallbackToDestructiveMigration() // Handle migrations
            .build()
    }
}