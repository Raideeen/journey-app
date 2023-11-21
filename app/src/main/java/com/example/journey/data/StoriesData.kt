package com.example.journey.data

import com.example.journey.R
import com.example.journey.model.Story

object StoriesData {
    fun getStoriesData(): ArrayList<Story> {
        return arrayListOf(
            Story(
                id = 1,
                titleResourceId = R.string.china_title,
                subTitleResourceId = R.string.china_subtitle,
                imageResourceId = R.drawable.img_wall_of_china,
                storyImageBanner = R.drawable.img_wall_of_china_selfie,
                storyDetails = R.string.story_detail_text,
            ),
            Story(
                id = 2,
                titleResourceId = R.string.egypt_title,
                subTitleResourceId = R.string.egypt_subtitle,
                imageResourceId = R.drawable.img_egypt,
                storyImageBanner = R.drawable.img_egypt_selfie,
                storyDetails = R.string.story_detail_text,
            ),
            Story(
                id = 3,
                titleResourceId = R.string.italy_title,
                subTitleResourceId = R.string.italy_subtitle,
                imageResourceId = R.drawable.img_italy,
                storyImageBanner = R.drawable.img_italy_selfie,
                storyDetails = R.string.story_detail_text,
            ),
            Story(
                id = 4,
                titleResourceId = R.string.japan_title,
                subTitleResourceId = R.string.japan_subtitle,
                imageResourceId = R.drawable.img_japan,
                storyImageBanner = R.drawable.img_japan,
                storyDetails = R.string.story_detail_text,
            ),
            Story(
                id = 5,
                titleResourceId = R.string.mexico_title,
                subTitleResourceId = R.string.mexico_subtitle,
                imageResourceId = R.drawable.img_mexico,
                storyImageBanner = R.drawable.img_mexico_selfie,
                storyDetails = R.string.story_detail_text,
            ),
            Story(
                id = 6,
                titleResourceId = R.string.peru_title,
                subTitleResourceId = R.string.peru_subtitle,
                imageResourceId = R.drawable.img_peru,
                storyImageBanner = R.drawable.img_peru_selfie,
                storyDetails = R.string.story_detail_text,
            )
        )
    }
}