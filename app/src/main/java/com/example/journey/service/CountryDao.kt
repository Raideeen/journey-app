package com.example.journey.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.journey.model.CountryEntity

/**
 * Data Access Object (DAO) for the CountryEntity table in the Room database.
 * This interface defines the methods for interacting with the country_table in the database.
 */
@Dao
interface CountryDao {

    /**
     * Retrieves a list of countries from the database that belong to a specific region.
     *
     * @param region The region to fetch countries for.
     * @return A list of CountryEntity objects that belong to the specified region.
     */
    @Query("SELECT * FROM country_table WHERE region = :region")
    suspend fun getCountriesByRegion(region: String): List<CountryEntity>

    /**
     * Inserts a list of countries into the database.
     * If a country with the same primary key already exists in the database, it will be replaced.
     *
     * @param countries The list of CountryEntity objects to be inserted into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CountryEntity>)
}