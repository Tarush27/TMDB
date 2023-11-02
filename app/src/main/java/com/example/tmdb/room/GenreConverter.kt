package com.example.tmdb.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GenreConverter {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {

        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun frmArrayList(list: ArrayList<String?>): String {
        return Gson().toJson(list)
    }
}