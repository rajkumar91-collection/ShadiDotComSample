package com.rajkumar.shadidotcom.repository

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Converters {
    @TypeConverter
    fun fromString(value: String?): Array<String> {
        val listType: Type = object : TypeToken<Array<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Array<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}