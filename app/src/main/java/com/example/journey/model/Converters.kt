package com.example.journey.model

import androidx.room.TypeConverter
import com.example.journey.model.Flags
import com.example.journey.model.Name
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromFlags(flags: Flags): String {
        return gson.toJson(flags)
    }

    @TypeConverter
    fun toFlags(flagsString: String): Flags {
        return gson.fromJson(flagsString, Flags::class.java)
    }

    @TypeConverter
    fun fromName(name: Name): String {
        return gson.toJson(name)
    }

    @TypeConverter
    fun toName(nameString: String): Name {
        return gson.fromJson(nameString, Name::class.java)
    }

    @TypeConverter
    fun fromCapital(capital: List<String>): String {
        return gson.toJson(capital)
    }

    @TypeConverter
    fun toCapital(capitalString: String): List<String> {
        return gson.fromJson(capitalString, Array<String>::class.java).toList()
    }
}