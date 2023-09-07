package com.demo.weather.location

interface LocationService {
    fun setup()
    fun startUpdates(duration: Long = 5000, location: (Location) -> Unit)
    fun stopUpdates()
    suspend fun getLastLocation(): Location
    var minimumAccuracy: Float
}