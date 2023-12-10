package com.example.journey

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.journey.model.StoryEntity
import com.example.journey.service.StoryDatabase
import com.example.journey.service.StoryRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StoryUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Mock
    private lateinit var mockRepository: StoryRepository

    @Mock
    private lateinit var mockDatabase: StoryDatabase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        // Initialize the mock database and repository
        mockDatabase = Mockito.mock(StoryDatabase::class.java)
        mockRepository = Mockito.mock(StoryRepository::class.java)

        val storyList = listOf(
            StoryEntity(
                title = "My Story",
                subtitle = "My Subtitle",
                imageUri = "https://images.unsplash.com/photo-1683009427041-d810728a7cb6?q=80&w=2486&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                storyDetails = "Details"
            )
        )
        val liveData = MutableLiveData<List<StoryEntity>>()
        liveData.value = storyList

        Mockito.`when`(mockRepository.allStories).thenReturn(liveData)
    }

    @Test
    fun loginStoryTest() {
        onView(withId(R.id.loginUsername)).apply {
            perform(click())
            perform(typeText("user"))

        }
        onView(withId(R.id.loginPassword)).apply {
            perform(click())
            perform(typeText("1234"))
        }
        onView(withId(R.id.loginButton)).perform(click())
    }

    @Test
    fun testStory() {
        loginStory()

        onView(withId(R.id.addStoryButton)).perform(ViewActions.scrollTo()).perform(click())

        // Fill in the fields and click the save button
        onView(withId(R.id.titleEditText)).apply {
            perform(click())
            perform(typeText("My Story"))
        }
        onView(withId(R.id.subtitleEditText)).apply {
            perform(click())
            perform(typeText("My Subtitle"))
        }
    }

    private fun loginStory() {
        onView(withId(R.id.loginUsername)).apply {
            perform(click())
            perform(typeText("user"))

        }
        onView(withId(R.id.loginPassword)).apply {
            perform(click())
            perform(typeText("1234"))
        }
        onView(withId(R.id.loginButton)).perform(click())
    }
}