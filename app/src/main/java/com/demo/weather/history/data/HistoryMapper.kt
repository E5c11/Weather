package com.demo.weather.history.data

import com.demo.weather.weather.data.weather.Weather

fun List<Weather>.toEntity() = WeatherEntity(
    weather = this,
    station = this[0].station
)

fun WeatherEntity.toList() = this.weather