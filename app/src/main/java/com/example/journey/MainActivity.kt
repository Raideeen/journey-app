package com.example.journey

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.journey.adapter.StoryAdapter
import com.example.journey.data.StoriesData
import com.example.journey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // * Set the content from the binding layout and not from the activity_main.xml
        setContentView(binding.root)

        // Initialize data.
        val storyList = StoriesData.getStoriesData()

        // * Set the adapter on the RecyclerView.
        storyAdapter = StoryAdapter { story ->
            Toast.makeText(
                this,
                "${story.titleResourceId}",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.recyclerView.adapter = storyAdapter

        // * Submit the list to the adapter.
        storyAdapter.submitList(storyList)
    }
}