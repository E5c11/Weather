package com.demo.weather.weather

import com.demo.weather.common.helper.Constant.LOADING
import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.data.exception.WeatherFetchException
import com.demo.weather.weather.data.stations.Station
import com.demo.weather.weather.data.toStation
import com.demo.weather.weather.helper.WeatherFlow
import com.demo.weather.weather.helper.WeatherListFlow
import com.demo.weather.weather.io.Frequency
import com.demo.weather.weather.io.WeatherDataSource
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.flow

class WeatherRepository(private val dataSource: WeatherDataSource) {

    fun fetchWeather(
        stationId: String, stationName: String, latLng: LatLng, frequency: Frequency = Frequency.HOURLY
    ): WeatherListFlow = flow {
        emit(Resource.loading(LOADING))
        try {
            val result = dataSource.getWeather(stationId, stationName, latLng, frequency)
            emit(Resource.success(result))
        } catch (e: WeatherFetchException) {
            emit(Resource.error(error = e))
        } catch (e: Exception) {
            emit(Resource.error(WeatherFetchException(error = e)))
        }
    }

    fun fetchCurrentWeather(
        stationId: String, stationName: String, latLng: LatLng, frequency: Frequency = Frequency.HOURLY
    ): WeatherFlow = flow {
        emit(Resource.loading(LOADING))
        try {
            val result = dataSource.getCurrentWeather(stationId, stationName, latLng, frequency)
            emit(Resource.success(result))
        } catch (e: WeatherFetchException) {
            emit(Resource.error(error = e))
        } catch (e: Exception) {
            emit(Resource.error(WeatherFetchException(error = e)))
        }
    }

    suspend fun fetchNearbyStations(lat: Double, lng: Double): List<Station> =
        dataSource.getStations(lat, lng)
}