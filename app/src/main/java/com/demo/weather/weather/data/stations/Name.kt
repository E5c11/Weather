package com.demo.weather.weather.data.stations

import com.google.gson.annotations.SerializedName

data class Name (
    @SerializedName("en" ) var en : String? = null
)