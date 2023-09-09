package com.demo.weather.weather.data.daily

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("date" ) var date : String? = null,
    @SerializedName("tavg" ) var tavg : Float? = null,
    @SerializedName("tmin" ) var tmin : Float? = null,
    @SerializedName("tmax" ) var tmax : Float? = null,
    @SerializedName("prcp" ) var prcp : Float? = null,
    @SerializedName("snow" ) var snow : String? = null,
    @SerializedName("wdir" ) var wdir : Int?    = null,
    @SerializedName("wspd" ) var wspd : Float? = null,
    @SerializedName("wpgt" ) var wpgt : String? = null,
    @SerializedName("pres" ) var pres : Float? = null,
    @SerializedName("tsun" ) var tsun : String? = null
)
