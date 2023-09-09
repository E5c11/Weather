package com.demo.weather.weather.data.stations

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("id"       ) var id       : String? = null,
    @SerializedName("name"     ) var name     : Name?   = Name(),
    @SerializedName("distance" ) var distance : Float?    = null
)