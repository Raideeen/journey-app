package com.example.journey

import com.example.journey.ui.story.StoryViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.journey.model.StoryEntity
import com.example.journey.service.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class StoryViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: StoryViewModel
    private lateinit var storyRepository: StoryRepository
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        storyRepository = mock(StoryRepository::class.java)
        viewModel = StoryViewModel(storyRepository)

        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun insertStory_invokesRepository() = runTest {
        val story = StoryEntity(title = "New Story", subtitle = "Subtitle", imageUri = "uri", storyDetails = "Details")
        viewModel.insert(story)

        advanceUntilIdle()

        verify(storyRepository).insert(story)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
