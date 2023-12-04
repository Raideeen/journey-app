package com.example.journey.ui.story

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.journey.R
import com.example.journey.databinding.FragmentModifyStoryBinding

class ModifyStoryFragment : Fragment(R.layout.fragment_modify_story) {

    private val storyViewModel: StoryViewModel by activityViewModels { StoryViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentModifyStoryBinding.bind(view)

        // Populate the EditText fields and ImageView with the current story details
        storyViewModel.currentStory.observe(viewLifecycleOwner) { story ->
            binding.storyTitleEdit.setText(story.title)
            binding.storySubtitleEdit.setText(story.subtitle)
            binding.storyImageDetail.load(story.imageUri)
        }

        // Save the changes and navigate back to StoryDetailsFragment when the button is clicked
        binding.saveButton.setOnClickListener {
            val updatedTitle = binding.storyTitleEdit.text.toString()
            val updatedSubtitle = binding.storySubtitleEdit.text.toString()
            storyViewModel.updateStoryTitle(updatedTitle)
            storyViewModel.updateStorySubtitle(updatedSubtitle)
            findNavController().navigate(R.id.action_modifyStoryFragment_to_notebookFragment)
        }
    }
}