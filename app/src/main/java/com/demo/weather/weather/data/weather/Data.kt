package com.demo.weather.weather.data.weather

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("time" ) var time : String? = null,
    @SerializedName("temp" ) var temp : Float?    = null,
    @SerializedName("dwpt" ) var dwpt : Float? = null,
    @SerializedName("rhum" ) var rhum : Float?    = null,
    @SerializedName("prcp" ) var prcp : Float?    = null,
    @SerializedName("snow" ) var snow : String? = null,
    @SerializedName("wdir" ) var wdir : Float?    = null,
    @SerializedName("wspd" ) var wspd : Float? = null,
    @SerializedName("wpgt" ) var wpgt : String? = null,
    @SerializedName("pres" ) var pres : Float? = null,
    @SerializedName("tsun" ) var tsun : String? = null,
    @SerializedName("coco" ) var coco : Int?    = null
)
