package com.demo.weather.weather.data.hourly

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("time" ) var time : String? = null,
    @SerializedName("temp" ) var temp : Int?    = null,
    @SerializedName("dwpt" ) var dwpt : Double? = null,
    @SerializedName("rhum" ) var rhum : Int?    = null,
    @SerializedName("prcp" ) var prcp : Int?    = null,
    @SerializedName("snow" ) var snow : String? = null,
    @SerializedName("wdir" ) var wdir : Int?    = null,
    @SerializedName("wspd" ) var wspd : Double? = null,
    @SerializedName("wpgt" ) var wpgt : String? = null,
    @SerializedName("pres" ) var pres : Double? = null,
    @SerializedName("tsun" ) var tsun : String? = null,
    @SerializedName("coco" ) var coco : Int?    = null
)
