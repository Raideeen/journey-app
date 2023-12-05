package com.example.journey.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_table")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val flags: Flags,
    val name: Name,
    val capital: List<String>,
    val region: String
)

data class Flags(
    val png: String,
    val svg: String,
    val alt: String
)

data class Name(
    val common: String,
    val official: String,
    val nativeName: Map<String, NativeName>
)

data class NativeName(
    val official: String,
    val common: String
)
