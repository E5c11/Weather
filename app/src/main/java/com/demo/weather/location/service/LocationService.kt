package com.demo.weather.location.service

import com.demo.weather.location.data.Location

interface LocationService {
    fun setup()
    fun startUpdates(duration: Long = 5000, location: (Location) -> Unit)
    fun stopUpdates()
    suspend fun getLastLocation(): Location
    var minimumAccuracy: Float
}