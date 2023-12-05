package com.example.journey.ui.country

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.journey.R
import com.example.journey.databinding.FragmentCountryIdeasBinding
import com.example.journey.model.CountryEntity
import com.example.journey.service.CountryRepository
import com.example.journey.service.RestCountriesService

class CountryIdeasFragment : Fragment() {

    private var _binding: FragmentCountryIdeasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CountryViewModel by viewModels {
        CountryViewModel.provideFactory(CountryRepository(RestCountriesService.create()))
    }

    private val adapter = CountryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryIdeasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()

        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            Log.d("CountryIdeasFragment", "onViewCreated: $countries")
            adapter.submitList(countries)
        }
    }

    private fun setupRecyclerView() {
        binding.countriesRecyclerView.adapter = adapter
    }

    private fun setupSpinner() {
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
                val region = parent.getItemAtPosition(position).toString()
                viewModel.fetchCountriesByRegion(region)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}