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

/**
 * ViewModel for the Story feature of the application.
 * This ViewModel is responsible for managing the data for the UI and communicating with the repository to perform database operations.
 * It exposes LiveData objects that the UI can observe.
 *
 * @property repository The repository this ViewModel will communicate with.
 */
class StoryViewModel(private val repository: StoryRepository) : ViewModel() {

    /**
     * Factory for creating instances of the StoryViewModel.
     * This factory is necessary because the StoryViewModel requires a StoryRepository, which is created using the application context.
     */
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

    // LiveData object exposing the list of all stories from the repository.
    public val allStories: LiveData<List<StoryEntity>> = repository.allStories

    // MutableLiveData object for the current story being viewed or edited.
    private var _currentStory: MutableLiveData<StoryEntity> = MutableLiveData<StoryEntity>()

    // MutableLiveData object for the Markdown content of the current story.
    private val _markdownContent = MutableLiveData<String>()

    // LiveData object exposing the current story.
    val currentStory: LiveData<StoryEntity>
        get() = _currentStory

    /**
     * Updates the current story.
     *
     * @param story The new current story.
     */
    fun updateCurrentStory(story: StoryEntity) {
        _currentStory.value = story
    }

    /**
     * Inserts a new story into the database.
     *
     * @param story The story to be inserted.
     */
    fun insert(story: StoryEntity) = viewModelScope.launch {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(story)
        }
    }

    /**
     * Updates the details of the current story and saves the updated story to the database.
     *
     * @param newDetail The new details for the current story.
     */
    fun updateStoryDetail(newDetail: String) {
        val currentStoryValue = _currentStory.value
        currentStoryValue?.let { story ->
            val updatedStory = story.copy(storyDetails = newDetail)
            _currentStory.value = updatedStory
            // Save the updated story to the database
            updateStoryInDatabase(updatedStory)

            // Update the Markdown content
            _markdownContent.value = newDetail
        }
    }

    /**
     * Updates a story in the database.
     *
     * @param story The story to be updated.
     */
    private fun updateStoryInDatabase(story: StoryEntity) = viewModelScope.launch {
        repository.update(story)
    }
}