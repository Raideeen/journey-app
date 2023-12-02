package com.example.journey.ui.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.journey.databinding.FragmentNotebookBinding

class StoryNotebookFragment : Fragment() {

    private var _binding: FragmentNotebookBinding? = null
    private val binding get() = _binding!!

    private lateinit var storyAdapter: StoryAdapter

    private val storyViewModel: StoryViewModel by viewModels { StoryViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotebookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupAddStoryButton()

        // Observing the list of stories from the ViewModel
        storyViewModel.allStories.observe(viewLifecycleOwner) { stories ->
            storyAdapter.submitList(stories)
        }
    }

    private fun setupRecyclerView() {
        storyAdapter = StoryAdapter { story ->
            // Handle story item click
            storyViewModel.updateCurrentStory(story)
            navigateToStoryDetails()
        }
        binding.recyclerView.adapter = storyAdapter
    }

    private fun setupAddStoryButton() {
        binding.addStoryButton.setOnClickListener {
            navigateToNewStoryFragment()
        }
    }

    private fun navigateToStoryDetails() {
        val action = StoryNotebookFragmentDirections.actionNotebookFragmentToDetailsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToNewStoryFragment() {
        val action = StoryNotebookFragmentDirections.actionNotebookFragmentToNewStoryFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
