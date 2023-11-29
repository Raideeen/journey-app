package com.example.journey.model

import com.example.journey.R

data class StoryEntity(
    val id: Int,
    val titleResourceId: Int,
    val subTitleResourceId: Int,
    val imageResourceId: Int,
    val storyImageBanner: Int,
    val storyDetails: Int = R.string.story_detail_text
)