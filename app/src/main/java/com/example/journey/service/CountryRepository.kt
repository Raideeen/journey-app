package com.example.journey.service

import android.content.Context
import com.example.journey.internet.NetworkUtils
import com.example.journey.internet.RestCountriesService
import com.example.journey.model.CountryEntity

class CountryRepository(
    private val apiService: RestCountriesService,
    private val countryDao: CountryDao,
    private val context: Context
) {
    suspend fun getCountriesByRegion(region: String): List<CountryEntity> {
        var countries = countryDao.getCountriesByRegion(region)
        if (countries.isEmpty()) {
            if (NetworkUtils.isNetworkAvailable(context)) {
                val response = apiService.getCountriesByRegion(region)
                if (response.isSuccessful) {
                    countries = response.body()?.map { it.copy(region = region) } ?: emptyList()
                    countryDao.insertAll(countries)
                }
            }
        }
        return countries
    }
}