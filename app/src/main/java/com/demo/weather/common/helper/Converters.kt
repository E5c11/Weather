package com.demo.weather.common.helper

import androidx.room.TypeConverter
import com.demo.weather.weather.data.weather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun weatherToString(value: List<Weather>) = gson.toJson(value)

    @TypeConverter
    fun stringToWeather(value: String): List<Weather> = gson.fromJson(value, object : TypeToken<List<Weather>>() {}.type)
}