package com.example.journey.ui.story

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import coil.load
import com.example.journey.R
import com.example.journey.databinding.FragmentDetailsBinding
import io.noties.markwon.Markwon

/**
 * StoryDetailsFragment is a Fragment that displays the details of a story.
 * It allows the user to switch between read mode and edit mode.
 * In read mode, the story details are rendered as markdown in a TextView.
 * In edit mode, the raw markdown text is displayed in an EditText for editing.
 * The raw markdown text is stored in a LiveData object, so it is observed at all times when the text changes.
 */
class StoryDetailsFragment : Fragment(R.layout.fragment_details) {

    // ViewModel for the story
    private val storyViewModel: StoryViewModel by activityViewModels { StoryViewModel.Factory }

    // Flag to indicate whether the fragment is in edit mode
    private var isEditMode = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)

        // Create a Markwon instance to render Markdown
        val markwon = Markwon.create(requireContext())

        // Observe the current story
        storyViewModel.currentStory.observe(viewLifecycleOwner) { story ->
            binding.storyTitleDetail.text = story.title
            binding.storySubtitleDetail.text = story.subtitle
            binding.storyImageDetail.load(story.imageUri)

            // Render markdown in TextView when not in edit mode
            if (!isEditMode) {
                markwon.setMarkdown(binding.storyDetail, story.storyDetails)
            }
        }

        // Setup menu provider for edit/read toggle
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Inflate the menu
                menuInflater.inflate(R.menu.menu_edit_read, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle menu item selection
                return when (menuItem.itemId) {
                    R.id.action_edit_read -> {
                        // Toggle between edit mode and read mode
                        toggleEditMode(binding, menuItem, markwon)
                        true
                    }
                    else -> false
                }
            }
        }

        // Add the MenuProvider to the Fragment
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    /**
     * Toggles between edit mode and read mode.
     * In edit mode, the EditText is populated with the raw markdown text for editing.
     * In read mode, the TextView is populated with the rendered markdown text.
     * @param binding The binding for the fragment's view.
     * @param menuItem The menu item that was selected.
     * @param markwon The Markwon instance used to render markdown.
     */
    private fun toggleEditMode(binding: FragmentDetailsBinding, menuItem: MenuItem, markwon: Markwon) {
        isEditMode = !isEditMode
        if (isEditMode) {
            // Switch to edit mode and populate EditText with raw markdown
            binding.storyDetailEdit.setText(storyViewModel.currentStory.value?.storyDetails)
            binding.storyDetail.visibility = View.GONE
            binding.storyDetailEdit.visibility = View.VISIBLE
            menuItem.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.read_icon)
        } else {
            // Switch to read mode, update ViewModel and render markdown
            val updatedText = binding.storyDetailEdit.text.toString()
            storyViewModel.updateStoryDetail(updatedText)
            markwon.setMarkdown(binding.storyDetail, updatedText)
            binding.storyDetail.visibility = View.VISIBLE
            binding.storyDetailEdit.visibility = View.GONE
            menuItem.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.edit_icon)
        }
    }
}