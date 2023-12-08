package com.example.journey.service

import androidx.lifecycle.LiveData
import com.example.journey.model.StoryEntity

/**
 * Repository for managing story data.
 * This repository is responsible for interacting with the StoryDao to perform database operations.
 * It provides methods for inserting and updating stories, and exposes a LiveData object that the UI can observe.
 *
 * @property storyDao The DAO this repository will communicate with.
 */
class StoryRepository(private val storyDao: StoryDao) {

    /**
     * LiveData object exposing the list of all stories from the DAO.
     */
    public val allStories: LiveData<List<StoryEntity>> = storyDao.getAllStories()

    /**
     * Inserts a story into the database.
     * This method is a suspend function, which means it should be called from a coroutine or another suspend function.
     *
     * @param story The StoryEntity object to be inserted into the database.
     */
    suspend fun insert(story: StoryEntity) {
        storyDao.insert(story)
    }

    /**
     * Updates a story in the database.
     * This method is a suspend function, which means it should be called from a coroutine or another suspend function.
     *
     * @param story The StoryEntity object to be updated in the database.
     */
    suspend fun update(story: StoryEntity) {
        storyDao.update(story)
    }
}