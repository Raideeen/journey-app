package com.example.journey.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.journey.data.StoriesData
import com.example.journey.model.StoryEntity

class StoryViewModel : ViewModel() {

    private var _currentStory: MutableLiveData<StoryEntity> = MutableLiveData<StoryEntity>()
    val currentStory: LiveData<StoryEntity>
        get() = _currentStory

    private var _storiesData: ArrayList<StoryEntity> = ArrayList()
    val storiesData: ArrayList<StoryEntity>
        get() = _storiesData

    init {
        // Initialize the stories data.
        _storiesData = StoriesData.getStoriesData()
        _currentStory.value = _storiesData[0]
    }

    fun updateCurrentStory(story: StoryEntity) {
        _currentStory.value = story
    }

    fun addStory(story: StoryEntity) {
        _storiesData.add(story)
    }
}