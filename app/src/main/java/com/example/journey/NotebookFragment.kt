package com.example.journey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.journey.adapter.StoryAdapter
import com.example.journey.data.StoriesData
import com.example.journey.databinding.FragmentNotebookBinding

class NotebookFragment : Fragment() {
    private lateinit var binding: FragmentNotebookBinding
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notebook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNotebookBinding.bind(view)

        // * Set the content from the binding layout and not from the activity_main.xml

        // Initialize data.
        val storyList = StoriesData.getStoriesData()

        // * Set the adapter on the RecyclerView.
        storyAdapter = StoryAdapter { story ->
            Toast.makeText(context, getString(story.titleResourceId), Toast.LENGTH_SHORT).show()
            // Navigate to the details screen. (not yet with the selected story)
            val action = NotebookFragmentDirections.actionNotebookFragmentToDetailsFragment()
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = storyAdapter

        // * Submit the list to the adapter.
        storyAdapter.submitList(storyList)
    }
}