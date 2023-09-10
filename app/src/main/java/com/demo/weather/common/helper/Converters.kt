package com.demo.weather.common.helper

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun latlngToString(value: LatLng) = gson.toJson(value)

    @TypeConverter
    fun stringToLatlng(value: String): LatLng = gson.fromJson(value, object : TypeToken<LatLng>() {}.type)
}