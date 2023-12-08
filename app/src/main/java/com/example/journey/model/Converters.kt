package com.example.journey.model

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * A class that provides methods to convert custom objects to and from a format that can be stored in the database.
 * It uses Gson to convert objects to their JSON representation and vice versa.
 * The @TypeConverter annotation indicates to Room to use these methods when accessing the database.
 */
class Converters {
    private val gson = Gson()

    /**
     * Converts a Flags object to a JSON string.
     *
     * @param flags The Flags object to be converted.
     * @return The JSON string representation of the Flags object.
     */
    @TypeConverter
    fun fromFlags(flags: Flags): String {
        return gson.toJson(flags)
    }

    /**
     * Converts a JSON string to a Flags object.
     *
     * @param flagsString The JSON string to be converted.
     * @return The Flags object represented by the JSON string.
     */
    @TypeConverter
    fun toFlags(flagsString: String): Flags {
        return gson.fromJson(flagsString, Flags::class.java)
    }

    /**
     * Converts a Name object to a JSON string.
     *
     * @param name The Name object to be converted.
     * @return The JSON string representation of the Name object.
     */
    @TypeConverter
    fun fromName(name: Name): String {
        return gson.toJson(name)
    }

    /**
     * Converts a JSON string to a Name object.
     *
     * @param nameString The JSON string to be converted.
     * @return The Name object represented by the JSON string.
     */
    @TypeConverter
    fun toName(nameString: String): Name {
        return gson.fromJson(nameString, Name::class.java)
    }

    /**
     * Converts a list of strings to a JSON string.
     *
     * @param capital The list of strings to be converted.
     * @return The JSON string representation of the list of strings.
     */
    @TypeConverter
    fun fromCapital(capital: List<String>): String {
        return gson.toJson(capital)
    }

    /**
     * Converts a JSON string to a list of strings.
     *
     * @param capitalString The JSON string to be converted.
     * @return The list of strings represented by the JSON string.
     */
    @TypeConverter
    fun toCapital(capitalString: String): List<String> {
        return gson.fromJson(capitalString, Array<String>::class.java).toList()
    }
}