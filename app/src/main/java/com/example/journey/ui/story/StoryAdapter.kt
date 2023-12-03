package com.example.journey.ui.story

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.journey.databinding.NotebookListItemBinding
import com.example.journey.model.StoryEntity

/**
 * Adapter for the RecyclerView in StoryNotebookFragment.
 * This adapter displays a list of StoryEntity objects.
 *
 * @property onItemClicked function to be invoked when a story item is clicked.
 */
class StoryAdapter(private val onItemClicked: (StoryEntity) -> Unit) :
    ListAdapter<StoryEntity, StoryAdapter.StoryViewHolder>(DiffCallback) {

    // Context of the parent ViewGroup
    private lateinit var context: Context

    /**
     * ViewHolder for the RecyclerView items.
     * Holds the layout for a single item.
     *
     * @property binding Binding object for the item layout.
     */
    class StoryViewHolder(private var binding: NotebookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds a StoryEntity to the item layout.
         *
         * @param storyEntity The StoryEntity to be bound.
         * @param context The context in which the ViewHolder is being used.
         */
        fun bind(storyEntity: StoryEntity, context: Context) {
            binding.title.text = storyEntity.title
            binding.subtitle.text = storyEntity.subtitle
            binding.description.text = storyEntity.storyDetails
            binding.travelImage.load(storyEntity.imageUri)
        }
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        context = parent.context
        val binding = NotebookListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StoryViewHolder(binding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val currentStory = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(currentStory)
        }
        holder.bind(currentStory, context)
    }

    companion object {
        /**
         * Provides a callback for calculating the diff between two non-null items in a list.
         */
        private val DiffCallback = object : DiffUtil.ItemCallback<StoryEntity>() {
            /**
             * Called to check whether two objects represent the same item.
             *
             * @param oldItem The StoryEntity in the old list.
             * @param newItem The StoryEntity in the new list.
             * @return True if the two items represent the same object or false if they are different.
             */
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem.id == newItem.id ||
                        oldItem.title == newItem.title
            }

            /**
             * Called to check whether two items have the same data.
             * This information is used to detect if the contents of an item have changed.
             *
             * @param oldItem The StoryEntity in the old list.
             * @param newItem The StoryEntity in the new list.
             * @return True if the contents of the items are the same or false if they are different.
             */
            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}