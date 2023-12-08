package com.example.journey.ui.country

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.journey.R
import com.example.journey.model.CountryEntity

/**
 * Adapter for the RecyclerView in CountryFragment.
 * This adapter displays a list of CountryEntity objects.
 */
class CountryAdapter : ListAdapter<CountryEntity, CountryAdapter.CountryViewHolder>(CountryDiffCallback) {

    /**
     * ViewHolder for the RecyclerView items.
     * Holds the layout for a single item.
     *
     * @property itemView The view of the item layout.
     */
    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flagImageView: ImageView = itemView.findViewById(R.id.countryImage)
        val countryNameTextView: TextView = itemView.findViewById(R.id.countryName)
        val countryOfficialNameTextView: TextView = itemView.findViewById(R.id.officialName)
        val capitalNameTextView: TextView = itemView.findViewById(R.id.capitalName)
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return CountryViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = getItem(position)
        Log.d("CountryAdapter", "country: $country")
        Log.d("CountryAdapter", "country.name.common: ${country.name.common}")
        Log.d("CountryAdapter", "country.name.official: ${country.name.official}")
        Log.d("CountryAdapter", "country.capital.firstOrNull(): ${country.capital.firstOrNull()}")
        Log.d("CountryAdapter", "country.flags.svg: ${country.flags.svg}")

        holder.countryNameTextView.text = country.name.common
        holder.countryOfficialNameTextView.text = country.name.official
        holder.capitalNameTextView.text = country.capital.firstOrNull()

        // Load flag image into flagImageView using Coil
        holder.flagImageView.load(country.flags.png) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.outline_error_24)
        }
    }

    /**
     * Provides a callback for calculating the diff between two non-null items in a list.
     */
    object CountryDiffCallback : DiffUtil.ItemCallback<CountryEntity>() {
        /**
         * Called to check whether two objects represent the same item.
         *
         * @param oldItem The CountryEntity in the old list.
         * @param newItem The CountryEntity in the new list.
         * @return True if the two items represent the same object or false if they are different.
         */
        override fun areItemsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean {
            return oldItem.name == newItem.name
        }

        /**
         * Called to check whether two items have the same data.
         * This information is used to detect if the contents of an item have changed.
         *
         * @param oldItem The CountryEntity in the old list.
         * @param newItem The CountryEntity in the new list.
         * @return True if the contents of the items are the same or false if they are different.
         */
        override fun areContentsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean {
            return oldItem == newItem
        }
    }
}