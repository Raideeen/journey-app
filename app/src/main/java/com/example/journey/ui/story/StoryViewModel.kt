package com.example.journey.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.journey.model.StoryEntity
import com.example.journey.service.StoryDatabase
import com.example.journey.service.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoryViewModel(private val repository: StoryRepository) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[APPLICATION_KEY]!!.applicationContext
                val storyDao = StoryDatabase.getDao(context)
                val storyRepository = StoryRepository(storyDao)
                StoryViewModel(storyRepository)
            }
        }
    }
    public val allStories: LiveData<List<StoryEntity>> = repository.allStories

    private var _currentStory: MutableLiveData<StoryEntity> = MutableLiveData<StoryEntity>()
    val currentStory: LiveData<StoryEntity>
        get() = _currentStory


    fun updateCurrentStory(story: StoryEntity) {
        _currentStory.value = story
    }

    fun addStory(story: StoryEntity) {
        insert(story)
    }

    fun insert(story: StoryEntity) = viewModelScope.launch {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(story)
        }
    }

    fun updateStoryDetail(newDetail: String) {
        val currentStoryValue = _currentStory.value
        currentStoryValue?.let { story ->
            val updatedStory = story.copy(storyDetails = newDetail)
            _currentStory.value = updatedStory

            // Save the updated story to the database
            updateStoryInDatabase(updatedStory)
        }
    }

    private fun updateStoryInDatabase(story: StoryEntity) = viewModelScope.launch {
        repository.update(story)
    }
}