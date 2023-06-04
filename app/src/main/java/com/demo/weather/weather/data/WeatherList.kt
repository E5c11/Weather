package com.demo.weather.weather.data

import com.demo.weather.Weather
import com.demo.weather.Wind
import com.google.gson.annotations.SerializedName


data class WeatherList (

    @SerializedName("dt"         ) val dt         : Int?               = null,
    @SerializedName("main"       ) val main       : Main?              = Main(),
    @SerializedName("weather"    ) val weather    : ArrayList<Weather> = arrayListOf(),
    @SerializedName("clouds"     ) val clouds     : Clouds?            = Clouds(),
    @SerializedName("wind"       ) val wind       : Wind?              = Wind(),
    @SerializedName("visibility" ) val visibility : Int?               = null,
    @SerializedName("pop"        ) val pop        : Double?            = null,
    @SerializedName("rain"       ) val rain       : Rain?              = Rain(),
    @SerializedName("sys"        ) val sys        : Sys?               = Sys(),
    @SerializedName("dt_txt"     ) val dtTxt      : String?            = null

)