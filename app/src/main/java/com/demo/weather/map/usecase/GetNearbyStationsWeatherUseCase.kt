package com.demo.weather.map.usecase

import android.util.Log
import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.WeatherRepository
import com.demo.weather.weather.helper.WeatherFlow
import com.demo.weather.weather.io.Frequency
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class GetNearbyStationsWeatherUseCase(private val repository: WeatherRepository) {
    operator fun invoke(lat: Double, lng: Double): WeatherFlow  = flow {
        try {
            val stations = repository.fetchNearbyStations(lat, lng)
            stations.forEach { station ->
                delay(500) //This is due to API call limit
                repository.fetchCurrentWeather(
                    station.id, station.name, LatLng(lat, lng), Frequency.DAILY
                ).collect { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> emit(Resource.success(resource.data!!))
                        Resource.Status.ERROR -> emit(Resource.error(resource.error))
                        Resource.Status.LOADING -> emit(Resource.loading())
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("NEARBY_LOCATIONS", "ERROR: Nearby station data was not fetched", e)
        }
    }
}