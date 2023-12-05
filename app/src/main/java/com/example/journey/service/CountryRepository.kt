package com.example.journey.service

import com.example.journey.model.CountryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryRepository(private val apiService: RestCountriesService) {

    suspend fun getCountriesByRegion(region: String): List<CountryEntity> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getCountriesByRegion(region)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Error fetching countries: ${response.errorBody()?.string()}")
            }
        }
    }
}