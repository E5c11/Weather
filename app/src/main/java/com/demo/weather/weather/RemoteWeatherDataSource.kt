package com.demo.weather.weather

import android.util.Log
import com.demo.weather.common.helper.getDate
import com.demo.weather.common.helper.toDay
import com.demo.weather.weather.data.daily.DailyDto
import com.demo.weather.weather.io.WeatherApi
import com.demo.weather.weather.data.exception.WeatherFetchException
import com.demo.weather.weather.data.hourly.Hourly
import com.demo.weather.weather.data.hourly.HourlyDto
import com.demo.weather.weather.data.stations.Stations
import com.demo.weather.weather.data.toHourly
import javax.inject.Inject

class RemoteWeatherDataSource @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherDataSource {
    override suspend fun getStations(lat: Long, lng: Long): Stations = try {
        val result = weatherApi.getStations(lat.toString(), lng.toString())
        val body = result.body()
        Log.d("myT", "getStations: $body")
        if (result.isSuccessful && body != null) body
        else throw WeatherFetchException()
    } catch (e: Exception) {
        throw WeatherFetchException(error = e)
    }

    override suspend fun getHourly(key: String, name: String): Hourly = try {
        val result = weatherApi.getHourly(key, getDate().toDay(), getDate(1).toDay())
        val body = result.body()
        if (result.isSuccessful && body != null) body.toHourly(name)
        else throw WeatherFetchException()
    } catch (e: Exception) {
        throw WeatherFetchException(error = e)
    }

    override suspend fun getDaily(key: String, name: String): DailyDto = try {
        val result = weatherApi.getDaily(key, "start", "end")
        val body = result.body()
        if (result.isSuccessful && body != null) body
        else throw WeatherFetchException()
    } catch (e: Exception) {
        throw WeatherFetchException(error = e)
    }
}