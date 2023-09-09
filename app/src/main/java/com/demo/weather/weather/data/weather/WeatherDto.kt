package com.demo.weather.weather.data.weather

import com.google.gson.annotations.SerializedName

data class WeatherDto (
    @SerializedName("meta" ) var meta : Meta?           = Meta(),
    @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
)
