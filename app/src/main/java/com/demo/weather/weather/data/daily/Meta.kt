package com.demo.weather.weather.data.daily

import com.google.gson.annotations.SerializedName

data class Meta (
    @SerializedName("generated" ) var generated : String? = null
)