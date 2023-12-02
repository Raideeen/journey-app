package com.example.journey.ui.story

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import coil.load
import com.example.journey.R
import com.example.journey.databinding.FragmentDetailsBinding

private const val TAG = "StoryDetailsFragment"

class StoryDetailsFragment : Fragment(R.layout.fragment_details) {

    private val storyViewModel: StoryViewModel by viewModels { StoryViewModel.Factory }

    private var isEditMode = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)

        Log.d(
            TAG, "onViewCreated: ${storyViewModel.currentStory.value}"
        )

        // ? Attach an observer on the current story to update the UI when the data changes.
        storyViewModel.currentStory.observe(viewLifecycleOwner) { story ->
            binding.storyTitleDetail.text = story.title
            binding.storySubtitleDetail.text = story.subtitle
            binding.storyDetail.text = story.storyDetails
            binding.storyImageDetail.load(story.imageUri)
        }

        // ? Create a MenuProvider instance for the edit/read menu
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Inflate the menu
                menuInflater.inflate(R.menu.menu_edit_read, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle menu item selection
                return when (menuItem.itemId) {
                    R.id.action_edit_read -> {
                        isEditMode = !isEditMode
                        if (isEditMode) {
                            binding.storyDetail.visibility = View.GONE
                            binding.storyDetailEdit.visibility = View.VISIBLE
                            menuItem.title = "Read"
                        } else {
                            val updatedText = binding.storyDetailEdit.text.toString()
                            binding.storyDetail.text = updatedText
                            binding.storyDetail.visibility = View.VISIBLE
                            binding.storyDetailEdit.visibility = View.GONE
                            menuItem.title = "Edit"
                            // Update the story in the ViewModel
                            storyViewModel.updateStoryDetail(updatedText)
                        }
                        true
                    }
                    else -> false
                }
            }
        }

        // Add the MenuProvider to the Fragment
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
