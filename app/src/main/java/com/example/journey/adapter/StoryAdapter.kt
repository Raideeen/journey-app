package com.example.journey.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.journey.databinding.NotebookListItemBinding
import com.example.journey.model.Story

class StoryAdapter(private val onItemClicked: (Story) -> Unit) :
    ListAdapter<Story, StoryAdapter.StoryViewHolder>(DiffCallback) {

    private lateinit var context: Context

    class StoryViewHolder(private var binding: NotebookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: Story, context: Context) {
            binding.title.text = context.getString(story.titleResourceId)
            binding.subTitle.text = context.getString(story.subTitleResourceId)
            binding.description.text = context.getString(story.storyDetails)
            binding.travelImage.load(story.imageResourceId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        context = parent.context
        val binding = NotebookListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val currentStory = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(currentStory)
        }
        holder.bind(currentStory, context)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id // Adjust this based on your model
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}

//class StoryAdapter(
//    private val context: Context,
//    private val storyList: List<Story>
//) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
//
//    /*
//    * Adapter for the [RecyclerView] in [MainActivity]. Displays [Story] data object.
//     */
//    class StoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//        val storyTitle = view.findViewById<TextView>(R.id.title)
//        val storySubTitle = view.findViewById<TextView>(R.id.subTitle)
//        val storyDescription = view.findViewById<TextView>(R.id.description)
//        val storyImage = view.findViewById<ImageView>(R.id.travelImage)
//    }
//
//    /* *
//     * Create new view (invoked by the layout manager)
//     ? Note: we inflate `notebook_list_item.xml` here, which is the layout for each item in the list.
//     * */
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.notebook_list_item, parent, false)
//        return StoryViewHolder(view)
//    }
//
//    /* *
//     * Replace the contents of a view (invoked by the layout manager)
//     * */
//    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
//        val story = storyList[position]
//        holder.storyTitle.text = context.resources.getString(story.titleResourceId)
//        holder.storySubTitle.text = context.resources.getString(story.subTitleResourceId)
//        holder.storyDescription.text = context.resources.getString(story.storyDetails)
//        holder.storyImage.load(story.imageResourceId)
//    }
//
//    /* *
//    * Return the size of your dataset (invoked by the layout manager)
//    * */
//    override fun getItemCount() = storyList.size
//}