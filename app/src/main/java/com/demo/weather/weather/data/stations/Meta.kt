package com.demo.weather.weather.data.stations

import com.google.gson.annotations.SerializedName

data class Meta (
    @SerializedName("generated" ) var generated : String? = null
)