package com.example.journey.ui.story

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.journey.databinding.FragmentNotebookBinding

/**
 * A [Fragment] subclass that displays a list of stories.
 * This fragment is responsible for displaying the list of stories in a RecyclerView,
 * and navigating to the details of a story when a story item is clicked.
 */
class StoryNotebookFragment : Fragment() {

    // Binding object instance corresponding to the fragment layout
    private var _binding: FragmentNotebookBinding? = null
    private val binding get() = _binding!!

    // Adapter for the list of stories
    private lateinit var storyAdapter: StoryAdapter

    // ViewModel instance
    private val storyViewModel: StoryViewModel by activityViewModels { StoryViewModel.Factory }

    /**
     * Inflates the layout for this fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotebookBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called immediately after onCreateView() has returned, and fragment's view hierarchy has been instantiated.
     * It can be used to do final initialization once these pieces are in place, such as retrieving views or restoring state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupAddStoryButton()

        // Observing the list of stories from the ViewModel
        storyViewModel.allStories.observe(viewLifecycleOwner) { stories ->
            storyAdapter.submitList(stories)
        }
    }

    /**
     * Sets up the RecyclerView that will display the list of stories.
     */
    private fun setupRecyclerView() {
        storyAdapter = StoryAdapter { story ->
            // Handle story item click
            storyViewModel.updateCurrentStory(story)
            navigateToStoryDetails()
        }
        binding.recyclerView.adapter = storyAdapter
    }

    /**
     * Sets up the button that will navigate to the NewStoryFragment when clicked.
     */
    private fun setupAddStoryButton() {
        binding.addStoryButton.setOnClickListener {
            navigateToNewStoryFragment()
        }
    }

    /**
     * Navigates to the StoryDetailsFragment.
     */
    private fun navigateToStoryDetails() {
        val action = StoryNotebookFragmentDirections.actionNotebookFragmentToDetailsFragment()
        findNavController().navigate(action)
    }

    /**
     * Navigates to the NewStoryFragment.
     */
    private fun navigateToNewStoryFragment() {
        val action = StoryNotebookFragmentDirections.actionNotebookFragmentToNewStoryFragment()
        findNavController().navigate(action)
    }

    /**
     * Called when the view hierarchy associated with the fragment is being destroyed.
     * It allows the fragment to clean up resources associated with its View.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}