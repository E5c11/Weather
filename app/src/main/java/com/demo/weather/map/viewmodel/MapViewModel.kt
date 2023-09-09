package com.demo.weather.map.viewmodel

import androidx.lifecycle.ViewModel
import com.demo.weather.map.usecase.GetNearbyStationsWeatherUseCase
import com.demo.weather.weather.helper.WeatherFlow
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val nearbyStationsUseCase: GetNearbyStationsWeatherUseCase
): ViewModel() {

    private var _markers = mutableListOf<MarkerOptions>()
    fun addMarker(markerOptions: MarkerOptions) = _markers.add(markerOptions)
    fun isMarkerAdded(title: String) = _markers.any { it.title == title }

    fun getNearbyWeather(lat: Double, lng: Double): WeatherFlow = nearbyStationsUseCase(lat, lng)
}