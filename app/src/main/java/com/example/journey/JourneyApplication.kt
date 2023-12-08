package com.example.journey

import android.app.Application
import androidx.room.Room
import com.example.journey.model.Converters
import com.example.journey.service.CountryDatabase
import com.example.journey.service.StoryDatabase

/**
 * Custom Application class for the Journey app.
 * This class is responsible for initializing the Room databases used in the app.
 * It initializes the StoryDatabase and CountryDatabase when the application is created.
 */
class JourneyApplication : Application() {

    // Instance of the StoryDatabase
    private lateinit var storyDatabase: StoryDatabase

    // Instance of the CountryDatabase
    private lateinit var countryDatabase: CountryDatabase

    /**
     * Called when the application is starting, before any other application objects have been created.
     * This is where most initialization should go: calling Context-specific functions such as
     * setting up Room databases.
     */
    override fun onCreate() {
        super.onCreate()

        // Initialize the StoryDatabase
        storyDatabase = Room.databaseBuilder(this, StoryDatabase::class.java, "story_database")
            .fallbackToDestructiveMigration() // Handle migrations by destroying the old database
            .build()

        // Initialize the CountryDatabase
        countryDatabase = Room.databaseBuilder(this, CountryDatabase::class.java, "country_database")
            .fallbackToDestructiveMigration() // Handle migrations by destroying the old database
            .build()
    }
}