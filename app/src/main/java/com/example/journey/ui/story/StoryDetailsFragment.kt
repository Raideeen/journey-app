package com.example.journey.ui.story

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.journey.R
import com.example.journey.databinding.FragmentDetailsBinding

private const val TAG = "StoryDetailsFragment"

class StoryDetailsFragment : Fragment(R.layout.fragment_details) {

    private val storyViewModel: StoryViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)

        Log.d(
            TAG, "onViewCreated: ${storyViewModel.currentStory.value}"
        )

        // Attach an observer on the current story to update the UI when the data changes.
        storyViewModel.currentStory.observe(viewLifecycleOwner) { story ->
            //TODO: Update the UI
            binding.storyTitleDetail.text = story.title
            binding.storySubtitleDetail.text = story.subTitle
            binding.storyDetail.text = story.storyDetails
            binding.storyImageDetail.load(story.imageResourceId)
        }
    }
}