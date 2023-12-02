package com.example.journey

import androidx.annotation.WorkerThread
import com.example.journey.model.StoryEntity
import com.example.journey.model.StoryDao
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class StoryRepository(private val storyDao: StoryDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allStoryEntities: Flow<List<StoryEntity>> = storyDao.getAllById()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun insert(storyEntity: StoryEntity) {
        storyDao.upinsert(storyEntity)
    }
}