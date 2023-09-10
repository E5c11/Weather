package com.demo.weather.history.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.weather.R
import com.demo.weather.common.helper.getDate
import com.demo.weather.common.helper.toHour
import com.demo.weather.weather.data.weather.Weather
import com.google.android.gms.maps.model.LatLng

@Entity("weather_history_table")
data class WeatherEntity(
    val weather: List<Weather>,
    @PrimaryKey(autoGenerate = false) val station: String
)
