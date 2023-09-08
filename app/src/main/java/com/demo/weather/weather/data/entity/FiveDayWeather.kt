package com.demo.weather.weather.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.weather.weather.data.City
import com.demo.weather.weather.data.Weather
import com.google.gson.annotations.SerializedName

@Entity(tableName = "five_day_table")
data class FiveDayWeather (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("cod"     ) val cod     : String,
    @SerializedName("message" ) val message : Int,
    @SerializedName("cnt"     ) val cnt     : Int,
    @SerializedName("list"    ) val list    : List<Weather>,
    @SerializedName("city"    ) val city    : City
)