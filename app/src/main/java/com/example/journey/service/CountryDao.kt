package com.example.journey.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.journey.model.CountryEntity

@Dao
interface CountryDao {
    @Query("SELECT * FROM country_table")
    suspend fun getCountries(): List<CountryEntity>

    @Query("SELECT * FROM country_table WHERE region = :region")
    suspend fun getCountriesByRegion(region: String): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CountryEntity>)
}