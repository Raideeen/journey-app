package com.example.journey.service

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.journey.model.StoryEntity

/**
 * Data Access Object (DAO) for the StoryEntity table in the Room database.
 * This interface defines the methods for interacting with the story_table in the database.
 */
@Dao
interface StoryDao {

    /**
     * Inserts a story into the database.
     * If a story with the same primary key already exists in the database, it will be replaced.
     *
     * @param story The StoryEntity object to be inserted into the database.
     */
    @Insert
    fun insert(story: StoryEntity)

    /**
     * Updates a story in the database.
     * The story is identified by its primary key.
     *
     * @param story The StoryEntity object to be updated in the database.
     */
    @Update
    suspend fun update(story: StoryEntity)

    /**
     * Retrieves all stories from the database.
     *
     * @return A LiveData object containing a list of all StoryEntity objects in the database.
     */
    @Query("SELECT * FROM story_table")
    fun getAllStories(): LiveData<List<StoryEntity>>
}