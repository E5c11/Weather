package com.demo.weather.data

import com.google.gson.annotations.SerializedName


data class FiveDayWeather (

  @SerializedName("cod"     ) val cod     : String?         = null,
  @SerializedName("message" ) val message : Int?            = null,
  @SerializedName("cnt"     ) val cnt     : Int?            = null,
  @SerializedName("list"    ) val list    : ArrayList<List> = arrayListOf(),
  @SerializedName("city"    ) val city    : City?           = City()

)