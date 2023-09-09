package com.demo.weather.history.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.weather.R
import com.demo.weather.common.helper.getDate
import com.demo.weather.common.helper.toHour
import com.google.android.gms.maps.model.LatLng

@Entity("weather_history_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val station: String,
    val latlng: LatLng,
    val time: String? = getDate().toHour(),
    val temp: Int? = 0,
    val rain : Int? = 0,
    val wind: Int? = 0,
    val icon: Int? = R.drawable.cloud,
    val suggestion: String? = "beach_access/v12/24px.svg"
)
