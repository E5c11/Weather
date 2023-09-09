package com.demo.weather.weather.data

import com.demo.weather.common.helper.suggestionPath
import com.demo.weather.common.helper.toHour
import com.demo.weather.common.helper.weatherIcon
import com.demo.weather.weather.data.hourly.Weather
import com.demo.weather.weather.data.hourly.HourlyDto
import com.demo.weather.weather.data.stations.Station
import com.demo.weather.weather.data.stations.StationsDto
import com.google.android.gms.maps.model.LatLng

fun HourlyDto.toHourly(name: String, latLng: LatLng): List<Weather> = this.data.map {
    Weather(
        station = name.split("/")[0],
        latlng = latLng,
        time = it.time?.toHour(),
        temp = it.temp?.toInt(),
        rain = it.prcp?.toInt(),
        wind = it.wspd?.toInt(),
        icon = it.coco?.weatherIcon(),
        suggestion = it.coco?.suggestionPath()
    )
}

fun StationsDto.toStation(): List<Station> = this.data.map {
    Station(
        it.name?.en!!,
        it.id!!
    )
}

