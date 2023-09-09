package com.demo.weather.weather.data.weather

import com.google.gson.annotations.SerializedName

data class Meta (
    @SerializedName("generated" ) var generated : String? = null
)
