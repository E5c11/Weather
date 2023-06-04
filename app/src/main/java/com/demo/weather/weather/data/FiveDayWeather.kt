package com.demo.weather.weather.data

import com.google.gson.annotations.SerializedName


data class FiveDayWeather (

    @SerializedName("cod"     ) val cod     : String,
    @SerializedName("message" ) val message : Int,
    @SerializedName("cnt"     ) val cnt     : Int,
    @SerializedName("list"    ) val list    : List<Weather>,
    @SerializedName("city"    ) val city    : City

)