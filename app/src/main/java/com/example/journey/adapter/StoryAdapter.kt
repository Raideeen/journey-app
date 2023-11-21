package com.example.journey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.journey.R
import com.example.journey.model.Story

class StoryAdapter(
    private val context: Context,
    private val storyList: List<Story>
) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    /*
    * Adapter for the [RecyclerView] in [MainActivity]. Displays [Story] data object.
     */
    class StoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val storyTitle = view.findViewById<TextView>(R.id.title)
        val storySubTitle = view.findViewById<TextView>(R.id.subTitle)
        val storyDescription = view.findViewById<TextView>(R.id.description)
        val storyImage = view.findViewById<ImageView>(R.id.travelImage)
    }

    /* *
     * Create new view (invoked by the layout manager)
     ? Note: we inflate `notebook_list_item.xml` here, which is the layout for each item in the list.
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notebook_list_item, parent, false)
        return StoryViewHolder(view)
    }

    /* *
     * Replace the contents of a view (invoked by the layout manager)
     * */
    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = storyList[position]
        holder.storyTitle.text = context.resources.getString(story.titleResourceId)
        holder.storySubTitle.text = context.resources.getString(story.subTitleResourceId)
        holder.storyDescription.text = context.resources.getString(story.storyDetails)
        holder.storyImage.load(story.imageResourceId)
    }

    /* *
    * Return the size of your dataset (invoked by the layout manager)
    * */
    override fun getItemCount() = storyList.size
}