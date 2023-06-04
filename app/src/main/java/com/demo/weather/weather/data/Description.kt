package com.demo.weather.weather.data

import com.google.gson.annotations.SerializedName


data class Description (

  @SerializedName("id"          ) var id          : Int,
  @SerializedName("main"        ) var main        : String,
  @SerializedName("description" ) var description : String,
  @SerializedName("icon"        ) var icon        : String

)