package com.demo.weather.weather.io

import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.data.stations.StationsDto
import com.demo.weather.weather.io.Frequency
import com.google.android.gms.maps.model.LatLng

interface WeatherDataSource {
    suspend fun getStations(lat: Double, lng: Double): StationsDto
    suspend fun getWeather(key: String, name: String, latLng: LatLng, frequency: Frequency): List<Weather>
    suspend fun getCurrentWeather(key: String, name: String, latLng: LatLng, frequency: Frequency): Weather
}