package com.example.journey.ui.country

import android.os.Bundle
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

/**
 * A [Fragment] subclass that displays a list of countries based on the selected region.
 * This fragment is responsible for fetching the list of countries from the API and displaying them in a RecyclerView.
 * It also provides a spinner for the user to select a region.
 */
class CountryIdeasFragment : Fragment(R.layout.fragment_country_ideas) {

    // ViewModel for the country
    private val viewModel: CountryViewModel by viewModels {
        CountryViewModel.provideFactory(
            CountryRepository(
                RestCountriesService.create(),
                CountryDatabase.getDatabase(requireContext()).countryDao(),
                requireContext()
            )
        )
    }

    // Adapter for the list of countries
    private val adapter = CountryAdapter()

    // HashMap to store the list of countries for each region
    private val regionCountryMap = HashMap<String, List<CountryEntity>>()

    /**
     * Called when the view is created.
     * Initializes UI elements and sets up listeners.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCountryIdeasBinding.bind(view)

        setupRecyclerView(binding)
        setupSpinner(binding)
        observeViewModel(binding)
    }

    /**
     * Sets up the RecyclerView that will display the list of countries.
     *
     * @param binding The binding for the fragment's view.
     */
    private fun setupRecyclerView(binding: FragmentCountryIdeasBinding) {
        binding.countriesRecyclerView.adapter = adapter
    }

    /**
     * Sets up the spinner that allows the user to select a region.
     *
     * @param binding The binding for the fragment's view.
     */
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
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                handleRegionSelection(parent, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    /**
     * Sets up the observers for the ViewModel's LiveData objects.
     *
     * @param binding The binding for the fragment's view.
     */
    private fun observeViewModel(binding: FragmentCountryIdeasBinding) {
        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            handleCountriesUpdate(countries, binding)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingSpinner.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Handles the update of the list of countries.
     *
     * @param countries The new list of countries.
     * @param binding The binding for the fragment's view.
     */
    private fun handleCountriesUpdate(countries: List<CountryEntity>, binding: FragmentCountryIdeasBinding) {
        if (countries.isEmpty()) {
            Toast.makeText(context, "No internet connection. Unable to fetch data...", Toast.LENGTH_LONG).show()
        } else {
            val selectedRegion = binding.regionSpinner.selectedItem.toString()
            regionCountryMap[selectedRegion] = countries
            adapter.submitList(countries)
        }
    }

    /**
     * Handles the selection of a region in the spinner.
     *
     * @param parent The AdapterView where the selection happened.
     * @param position The position of the item in the adapter that was selected.
     */
    private fun handleRegionSelection(parent: AdapterView<*>, position: Int) {
        val region = parent.getItemAtPosition(position).toString()
        if (regionCountryMap.containsKey(region)) {
            adapter.submitList(regionCountryMap[region])
        } else {
            adapter.submitList(emptyList())
            viewModel.fetchCountriesByRegion(region)
        }
    }
}