package com.example.journey.ui.country

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.journey.model.CountryEntity
import com.example.journey.service.CountryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for the Country feature of the application.
 * This ViewModel is responsible for managing the data for the UI and communicating with the repository to perform API operations.
 * It exposes LiveData objects that the UI can observe.
 *
 * @property repository The repository this ViewModel will communicate with.
 */
class CountryViewModel(private val repository: CountryRepository) : ViewModel() {

    /**
     * Factory for creating instances of the CountryViewModel.
     * This factory is necessary because the CountryViewModel requires a CountryRepository, which is created using the application context.
     */
    companion object {
        fun provideFactory(repository: CountryRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return CountryViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }

    // LiveData object exposing the list of all countries from the repository.
    private val _countries = MutableLiveData<List<CountryEntity>>()
    val countries: LiveData<List<CountryEntity>>
        get() = _countries

    // LiveData object exposing the loading state of the ViewModel.
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    // LiveData object exposing any error messages that occur during API operations.
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    /**
     * Fetches the list of countries by region from the API and updates the LiveData objects.
     *
     * @param region The region to fetch countries for.
     */
    fun fetchCountriesByRegion(region: String) {
        viewModelScope.launch {
            _isLoading.value = true
            var result = repository.getCountriesByRegion(region)
            while (result.isEmpty()) {
                delay(1000)
                result = repository.getCountriesByRegion(region)
            }
            Log.d("CountryViewModel", "fetchCountriesByRegion: $result")
            _countries.value = result
            _isLoading.value = false
        }
    }
}