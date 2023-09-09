package com.demo.weather.weather.data.daily

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("date" ) var date : String? = null,
    @SerializedName("tavg" ) var tavg : Double? = null,
    @SerializedName("tmin" ) var tmin : Double? = null,
    @SerializedName("tmax" ) var tmax : Double? = null,
    @SerializedName("prcp" ) var prcp : Double? = null,
    @SerializedName("snow" ) var snow : String? = null,
    @SerializedName("wdir" ) var wdir : Int?    = null,
    @SerializedName("wspd" ) var wspd : Double? = null,
    @SerializedName("wpgt" ) var wpgt : String? = null,
    @SerializedName("pres" ) var pres : Double? = null,
    @SerializedName("tsun" ) var tsun : String? = null
)
