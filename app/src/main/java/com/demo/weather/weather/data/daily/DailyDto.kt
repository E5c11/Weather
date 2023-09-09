package com.demo.weather.weather.data.daily

import com.google.gson.annotations.SerializedName

data class DailyDto (
    @SerializedName("meta" ) var meta : Meta?           = Meta(),
    @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
)