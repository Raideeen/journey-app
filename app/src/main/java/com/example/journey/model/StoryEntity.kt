package com.example.journey.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.journey.R
import java.sql.Date

@Entity(tableName = "story_entity_item")
data class StoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subTitle") val subTitle: String,
    @ColumnInfo(name = "image") val imageResourceId: Int,
    @ColumnInfo(name = "imageBanner") val storyImageBanner: Int,
    @ColumnInfo(name = "storyDetails") val storyDetails: String?,
)