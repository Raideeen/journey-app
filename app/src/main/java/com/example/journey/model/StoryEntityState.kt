package com.example.journey.model

import com.example.journey.StoryEntitySortType

data class StoryEntityState (
    val storyEntities: List<StoryEntity> = emptyList(),
    val title: String = "",
    val subTitle: String = "",
    val storyDetail: String = "",
    val isEditingStory: Boolean = false,
    val sortType: StoryEntitySortType = StoryEntitySortType.TITLE
)