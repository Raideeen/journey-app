package com.example.journey.ui.country

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.journey.R
import com.example.journey.databinding.FragmentCountryIdeasBinding
import com.example.journey.internet.RestCountriesService
import com.example.journey.model.CountryEntity
import com.example.journey.service.CountryDatabase
import com.example.journey.service.CountryRepository

class CountryIdeasFragment : Fragment(R.layout.fragment_country_ideas) {

    private val viewModel: CountryViewModel by viewModels {
        CountryViewModel.provideFactory(
            CountryRepository(
                RestCountriesService.create(),
                CountryDatabase.getDatabase(requireContext()).countryDao(),
                requireContext()
            )
        )
    }
    private val adapter = CountryAdapter()

    private val regionCountryMap = HashMap<String, List<CountryEntity>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCountryIdeasBinding.bind(view)

        setupRecyclerView(binding)
        setupSpinner(binding)

        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            if (countries.isEmpty()) {
                // Show a message to the user
                Toast.makeText(context, "No internet connection. Enable to fetch data...", Toast.LENGTH_LONG).show()
            } else {
                Log.d("CountryIdeasFragment", "onViewCreated: $countries")
                val selectedRegion = binding.regionSpinner.selectedItem.toString()
                regionCountryMap[selectedRegion] = countries
                adapter.submitList(countries)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                // Show loading spinner
                Log.d("CountryIdeasFragment", "onViewCreated: isLoading")
                binding.loadingSpinner.visibility = View.VISIBLE
            } else {
                // Hide loading spinner
                Log.d("CountryIdeasFragment", "onViewCreated: not isLoading")
                binding.loadingSpinner.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // Show toast message
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView(binding: FragmentCountryIdeasBinding) {
        binding.countriesRecyclerView.adapter = adapter
    }

    private fun setupSpinner(binding: FragmentCountryIdeasBinding) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.regions_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.regionSpinner.adapter = adapter
        }

        binding.regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val region = parent.getItemAtPosition(position).toString()
                if (regionCountryMap.containsKey(region)) {
                    // If the list of countries for the selected region is already in the HashMap, use it
                    adapter.submitList(regionCountryMap[region])
                } else {
                    // Otherwise, fetch the countries from the API
                    adapter.submitList(emptyList())
                    viewModel.fetchCountriesByRegion(region)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }
        }
    }
}