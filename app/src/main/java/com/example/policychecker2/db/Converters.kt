package com.example.policychecker2.db

import androidx.room.TypeConverter
import com.example.policychecker2.db.models.terms.de.MietvertragDe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {


    @TypeConverter
    fun toMietvertragJson(resultsFound: List<MietvertragDe>) : String {
        val gson = Gson()
        return gson.toJson(resultsFound,  object : TypeToken<ArrayList<MietvertragDe>>() {}.type)
    }

    @TypeConverter
    fun fromMietvertragJson(json: String): List<MietvertragDe>{
        val list  = object : TypeToken<ArrayList<MietvertragDe>>() {}.type
        return Gson().fromJson(json, list)

    }

}