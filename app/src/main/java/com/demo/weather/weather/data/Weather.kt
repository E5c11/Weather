package com.demo.weather.weather.data

import com.demo.weather.Wind
import com.google.gson.annotations.SerializedName


data class Weather (

    @SerializedName("dt"         ) val dt         : Int,
    @SerializedName("main"       ) val mainWeather: MainWeather,
    @SerializedName("weather"    ) val description: List<description>,
    @SerializedName("clouds"     ) val clouds     : Clouds,
    @SerializedName("wind"       ) val wind       : Wind,
    @SerializedName("visibility" ) val visibility : Int,
    @SerializedName("pop"        ) val pop        : Double,
    @SerializedName("rain"       ) val rain       : Rain,
    @SerializedName("sys"        ) val sys        : Sys,
    @SerializedName("dt_txt"     ) val dtTxt      : String

)