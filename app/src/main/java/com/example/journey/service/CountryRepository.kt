package com.example.journey.service

import com.example.journey.internet.RestCountriesService
import com.example.journey.model.CountryEntity

class CountryRepository(
    private val apiService: RestCountriesService,
    private val countryDao: CountryDao
) {
    suspend fun getCountriesByRegion(region: String): List<CountryEntity> {
        var countries = countryDao.getCountriesByRegion(region)
        if (countries.isEmpty()) {
            val response = apiService.getCountriesByRegion(region)
            if (response.isSuccessful) {
                countries = response.body()?.map { it.copy(region = region) } ?: emptyList()
                countryDao.insertAll(countries)
            }
        }
        return countries
    }
}