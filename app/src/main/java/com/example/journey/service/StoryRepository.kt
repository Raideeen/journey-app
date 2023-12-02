package com.example.journey.service

import androidx.lifecycle.LiveData
import com.example.journey.model.StoryEntity

class StoryRepository(private val storyDao: StoryDao) {
    public val allStories: LiveData<List<StoryEntity>> = storyDao.getAllStories()

    suspend fun insert(story: StoryEntity) {
        storyDao.insert(story)
    }

    suspend fun update(story: StoryEntity) {
        storyDao.update(story)
    }
}