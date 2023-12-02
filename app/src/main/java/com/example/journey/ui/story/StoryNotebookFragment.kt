package com.example.journey.ui.story

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.journey.R
import com.example.journey.data.StoriesData
import com.example.journey.databinding.FragmentNotebookBinding

private const val TAG = "StoryNotebookFragment"

class StoryNotebookFragment : Fragment(R.layout.fragment_notebook) {
    private lateinit var binding: FragmentNotebookBinding
    private lateinit var storyAdapter: StoryAdapter

    private val storyViewModel: StoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notebook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // * Set the content from the binding layout and not from the activity_main.xml
        binding = FragmentNotebookBinding.bind(view)

        binding.addStoryButton.setOnClickListener {
            Log.d(TAG, "Navigation to NewStoryFragment")
            val action = StoryNotebookFragmentDirections.actionNotebookFragmentToNewStoryFragment()
            this.findNavController().navigate(action)
        }
        // Initialize data.
        val storyList = StoriesData.getStoriesData()

        // * Set the adapter on the RecyclerView.
        storyAdapter = StoryAdapter { story ->
            // Update the user selected story as the current story in the shared viewmodel
            storyViewModel.updateCurrentStory(story)

            Log.d(TAG, "onViewCreated: ${story.titleResourceId}")

            Toast.makeText(context, getString(story.titleResourceId), Toast.LENGTH_SHORT).show()

            // Navigate to the details screen. (not yet with the selected story)
            Log.d(TAG, "Navigation to DetailsFragment")
            val action =
                StoryNotebookFragmentDirections.actionNotebookFragmentToDetailsFragment()
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = storyAdapter

        // * Submit the list to the adapter.
        storyAdapter.submitList(storyList)
        Log.d(TAG, "Updated UI")
    }
}