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

class StoryAdapter(private val onItemClicked: (StoryEntity) -> Unit) :
    ListAdapter<StoryEntity, StoryAdapter.StoryViewHolder>(DiffCallback) {

    private lateinit var context: Context

    class StoryViewHolder(private var binding: NotebookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(storyEntity: StoryEntity, context: Context) {
            binding.title.text = context.getString(storyEntity.titleResourceId)
            binding.subTitle.text = context.getString(storyEntity.subTitleResourceId)
            binding.description.text = context.getString(storyEntity.storyDetails)
            binding.travelImage.load(storyEntity.imageResourceId)
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
        private val DiffCallback = object : DiffUtil.ItemCallback<StoryEntity>() {
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem.id == newItem.id ||
                        oldItem.titleResourceId == newItem.titleResourceId
            }

            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
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