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
import androidx.lifecycle.Lifecycle
import coil.load
import com.example.journey.R
import com.example.journey.databinding.FragmentDetailsBinding
import io.noties.markwon.Markwon
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.MarkwonEditorTextWatcher
import java.util.concurrent.Executors

private const val TAG = "StoryDetailsFragment"

class StoryDetailsFragment : Fragment(R.layout.fragment_details) {

    private val storyViewModel: StoryViewModel by activityViewModels { StoryViewModel.Factory }

    private var isEditMode = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)

        Log.d(
            TAG, "onViewCreated: ${storyViewModel.currentStory.value}"
        )

        // Create a Markwon instance to render Markdown
        val markwon = Markwon.create(requireContext())

        // Create a MarkwonEditor instance to render Markdown in an EditText
        val markwonEditor = MarkwonEditor.create(markwon)

        // ? Attach a MarkwonEditorTextWatcher to the EditText to render Markdown
        binding.storyDetailEdit.addTextChangedListener(
            MarkwonEditorTextWatcher.withPreRender(
                markwonEditor,
                Executors.newCachedThreadPool(),
                binding.storyDetailEdit
            )
        )

        // ? Attach an observer on the current story to update the UI when the data changes.
        storyViewModel.currentStory.observe(viewLifecycleOwner) { story ->
            binding.storyTitleDetail.text = story.title
            binding.storySubtitleDetail.text = story.subtitle
            binding.storyDetail.text = story.storyDetails
            binding.storyImageDetail.load(story.imageUri)

            // * Use Markwon to render the Markdown in the story details
            markwon.setMarkdown(binding.storyDetail, story.storyDetails)

            // * Use Markwon to render the Markdown in the EditText
            markwonEditor.process(binding.storyDetailEdit.text)
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
                            // * Keep the original text in the EditText so we doesn't lose it
                            // * when the user switches back to read mode.
                            binding.storyDetailEdit.setText(binding.storyDetail.text)
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
                            // Update the story in the database
                            storyViewModel.saveStory()
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
