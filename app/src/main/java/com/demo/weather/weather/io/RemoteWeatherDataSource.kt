package com.demo.weather.weather.io

import com.demo.weather.common.helper.getDate
import com.demo.weather.common.helper.toDay
import com.demo.weather.weather.data.exception.StationFetchException
import com.demo.weather.weather.data.exception.WeatherFetchException
import com.demo.weather.weather.data.stations.Station
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.data.stations.StationsDto
import com.demo.weather.weather.data.toHourly
import com.demo.weather.weather.data.toStation
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class RemoteWeatherDataSource @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherDataSource {
    override suspend fun getStations(lat: Double, lng: Double): List<Station> = try {
        val result = weatherApi.getStations(lat.toString(), lng.toString())
        val body = result.body()
        if (result.isSuccessful && body != null) body.toStation()
        else throw StationFetchException()
    }catch (e: Exception) {
        throw StationFetchException(error = e)
    }

    override suspend fun getWeather(
        key: String, name: String, latLng: LatLng, frequency: Frequency
    ): List<Weather> = try {
        val result = weatherApi.getWeather(key, getDate().toDay(), getDate(1).toDay(), frequency.value)
        val body = result.body()
        if (result.isSuccessful && body != null) body.toHourly(name, latLng)
        else throw WeatherFetchException()
    } catch (e: WeatherFetchException) {
        throw e
    } catch (e: Exception) {
        throw WeatherFetchException(error = e)
    }

    override suspend fun getCurrentWeather(
        key: String, name: String, latLng: LatLng, frequency: Frequency
    ): Weather = try {
        val today = getDate().toDay()
        val result = weatherApi.getWeather(key, today, today, frequency.value)
        val body = result.body()
        if (result.isSuccessful && body != null) body.toHourly(name, latLng)[0]
        else throw WeatherFetchException()
    } catch (e: WeatherFetchException) {
        throw e
    } catch (e: Exception) {
        throw WeatherFetchException(error = e)
    }
}