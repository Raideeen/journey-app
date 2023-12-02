package com.example.journey.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Query("Select * FROM story_entity_item ORDER BY id")
    fun getAllById() : Flow<List<StoryEntity>>

    @Query("Select * FROM story_entity_item ORDER BY title")
    fun getAllByTitle() : Flow<List<StoryEntity>>
    @Upsert
    suspend fun upinsert(storyEntity: StoryEntity)
    @Delete
    suspend fun deleteStoryEntity(storyEntity: StoryEntity)
    @Query("DELETE FROM story_entity_item")
    suspend fun deleteAll()
}