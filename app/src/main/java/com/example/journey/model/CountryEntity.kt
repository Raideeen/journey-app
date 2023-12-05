package com.example.journey.model

data class CountryEntity(
    val flags: Flags,
    val name: Name,
    val capital: List<String>
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
