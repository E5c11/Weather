package com.demo.weather.weather

import com.demo.weather.weather.data.daily.DailyDto
import com.demo.weather.weather.data.hourly.Hourly
import com.demo.weather.weather.data.stations.Stations

interface WeatherDataSource {
    suspend fun getStations(lat: Long, lng: Long): Stations
    suspend fun getHourly(key: String, name: String): Hourly
    suspend fun getDaily(key: String, name: String): DailyDto
}