package com.demo.weather.weather

import com.demo.weather.weather.data.FiveDayWeather
import com.demo.weather.weather.helper.WeatherConstants.API_KEY
import com.demo.weather.weather.io.WeatherApi
import com.demo.weather.weather.io.WeatherFetchException
import javax.inject.Inject

class RemoteWeatherDataSource @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherDataSource {
    override suspend fun getFiveDayWeather(lat: Long, lng: Long): FiveDayWeather {
        return try {
            val result = weatherApi.getFiveDay(lat.toString(), lng.toString(), "5", "metric", API_KEY)
            val body = result.body()
            if (result.isSuccessful && body != null) body
            else throw WeatherFetchException()
        } catch (e: Exception) {
            throw WeatherFetchException(error = e)
        }
    }
}