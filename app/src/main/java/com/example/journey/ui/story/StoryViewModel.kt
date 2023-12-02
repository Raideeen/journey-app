package com.example.journey.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.journey.StoryRepository
import com.example.journey.data.StoriesData
import com.example.journey.model.StoryEntity
import kotlinx.coroutines.launch

class StoryViewModel(
    private val repository: StoryRepository
) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allStories: LiveData<List<StoryEntity>> = repository.allStoryEntities.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(storyEntity: StoryEntity) = viewModelScope.launch {
        repository.insert(storyEntity)
    }

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


class StoryViewModelFactory(private val repository: StoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryEntity::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}