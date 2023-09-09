package com.demo.weather.weather.data.stations

import com.google.gson.annotations.SerializedName

data class Stations (
    @SerializedName("meta" ) var meta : Meta?           = Meta(),
    @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
)
