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
    val countries: LiveData<List<CountryEntity>>
        get() = _countries

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

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
