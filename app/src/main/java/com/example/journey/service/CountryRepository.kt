package com.example.journey.service

import android.content.Context
import com.example.journey.internet.NetworkUtils
import com.example.journey.internet.RestCountriesService
import com.example.journey.model.CountryEntity

/**
 * Repository for managing country data.
 * This repository is responsible for fetching country data from the API and storing it in the local database.
 * It provides a method for getting a list of countries by region.
 *
 * @property apiService The service used to interact with the REST Countries API.
 * @property countryDao The DAO used to interact with the country_table in the database.
 * @property context The context used to check network availability.
 */
class CountryRepository(
    private val apiService: RestCountriesService,
    private val countryDao: CountryDao,
    private val context: Context
) {
    /**
     * Fetches a list of countries by region.
     * If the list of countries for the specified region is not in the database, it fetches the list from the API and stores it in the database.
     * If the list of countries for the specified region is in the database, it returns the list from the database.
     * If the device is not connected to the internet, it returns the list from the database (which may be empty).
     *
     * @param region The region to fetch countries for.
     * @return A list of CountryEntity objects that belong to the specified region.
     */
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