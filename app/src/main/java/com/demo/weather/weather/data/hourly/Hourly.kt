package com.demo.weather.weather.data.hourly

import com.google.gson.annotations.SerializedName

data class Hourly (
    @SerializedName("meta" ) var meta : Meta?           = Meta(),
    @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
)
