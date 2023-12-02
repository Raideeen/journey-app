package com.example.journey

import android.app.Application

class StoryApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { StoryDataBase.getDataBase(this) }
    val repository by lazy { StoryRepository(database.storyDao()) }
}