package com.demo.weather.location.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val latitude: Double?,
    val longitude: Double?,
    val accuracy: Float?
): Parcelable
