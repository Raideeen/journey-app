package com.example.journey.service

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.journey.model.StoryEntity

@Dao
interface StoryDao {
    @Insert
    fun insert(story: StoryEntity)

    @Update
    suspend fun update(story: StoryEntity)

    @Query("SELECT * FROM story_table")
    fun getAllStories(): LiveData<List<StoryEntity>>
}