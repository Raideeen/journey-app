package com.example.journey.ui.story

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journey.R
import com.example.journey.StoryApplication
import com.example.journey.data.StoriesData
import com.example.journey.databinding.FragmentNotebookBinding

private const val TAG = "StoryNotebookFragment"

class StoryNotebookFragment : Fragment(R.layout.fragment_notebook) {
    private lateinit var binding: FragmentNotebookBinding
    private lateinit var storyAdapter: StoryAdapter

    //private val storyViewModel: StoryViewModel by activityViewModels()
    private val storyViewModel: StoryViewModel by viewModels {
        StoryViewModelFactory((activity?.application as StoryApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notebook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*super.onViewCreated(view, savedInstanceState)

        binding = FragmentNotebookBinding.bind(view)

        // * Set the content from the binding layout and not from the activity_main.xml

        // Initialize data.
        val storyList = StoriesData.getStoriesData()

        // * Set the adapter on the RecyclerView.
        storyAdapter = StoryAdapter { story ->
            // Update the user selected story as the current story in the shared viewmodel
            storyViewModel.updateCurrentStory(story)

            Log.d(TAG, "onViewCreated: ${story.title}")

            Toast.makeText(context, story.title, Toast.LENGTH_SHORT).show()

            // Navigate to the details screen. (not yet with the selected story)
            Log.d(TAG, "Navigation to DetailsFragment")
            val action =
                StoryNotebookFragmentDirections.actionNotebookFragmentToDetailsFragment()
            this.findNavController().navigate(action)
        }



        binding.recyclerView.adapter = storyAdapter

        // * Submit the list to the adapter.
        storyAdapter.submitList(storyList)
        Log.d(TAG, "Updated UI")*/

        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNotebookBinding.bind(view)

        // Initialize data.
        val storyList = StoriesData.getStoriesData()

        // Set up RecyclerView adapter
        storyAdapter = StoryAdapter { story ->
            storyViewModel.updateCurrentStory(story)
            Toast.makeText(context, story.title, Toast.LENGTH_SHORT).show()

            // Navigate to the details screen with the selected story
            val action = StoryNotebookFragmentDirections.actionNotebookFragmentToDetailsFragment()
            this.findNavController().navigate(action)
        }

        binding.recyclerView.adapter = storyAdapter

        // Observe the LiveData in the ViewModel
        storyViewModel.allStories.observe(viewLifecycleOwner, Observer { stories ->
            // Update your UI with the new data (e.g., update RecyclerView adapter)
            if (stories != null) {
                storyAdapter.submitList(stories)
                Log.d(TAG, "Updated UI with new data")
            } else {
                Log.e(TAG, "Received null list of stories")
            }
            storyAdapter.submitList(stories)
            Log.d(TAG, "Updated UI with new data")
        })

        // Submit the initial list to the adapter
        storyAdapter.submitList(storyList)
        Log.d(TAG, "Updated UI")
    }
}