package com.example.journey.ui.country

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.journey.model.CountryEntity
import com.example.journey.service.CountryRepository
import kotlinx.coroutines.launch

class CountryViewModel(private val repository: CountryRepository) : ViewModel() {

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
    private val _countries = MutableLiveData<List<CountryEntity>>()
    val countries: LiveData<List<CountryEntity>> = _countries

    fun fetchCountriesByRegion(region: String) {
        viewModelScope.launch {
            val result = repository.getCountriesByRegion(region)
            Log.d("CountryViewModel", "fetchCountriesByRegion: $result")
            _countries.value = result
        }
    }
}