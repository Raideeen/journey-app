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

class CountryAdapter : ListAdapter<CountryEntity, CountryAdapter.CountryViewHolder>(CountryDiffCallback) {

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flagImageView: ImageView = itemView.findViewById(R.id.countryImage)
        val countryNameTextView: TextView = itemView.findViewById(R.id.countryName)
        val countryOfficialNameTextView: TextView = itemView.findViewById(R.id.officialName)
        val capitalNameTextView: TextView = itemView.findViewById(R.id.capitalName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return CountryViewHolder(view)
    }

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

    object CountryDiffCallback : DiffUtil.ItemCallback<CountryEntity>() {
        override fun areItemsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean {
            return oldItem == newItem
        }
    }
}