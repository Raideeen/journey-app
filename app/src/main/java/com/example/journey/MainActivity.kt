package com.example.journey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.journey.adapter.StoryAdapter
import com.example.journey.data.StoriesData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize data.
        val storyList = StoriesData.getStoriesData()

        // Initialize recycler view.
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = StoryAdapter(this, storyList)

        // Improve performance by setting fixed size.
        recyclerView.setHasFixedSize(true)
    }
}