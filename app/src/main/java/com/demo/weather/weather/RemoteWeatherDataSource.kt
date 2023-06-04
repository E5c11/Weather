package com.demo.weather.weather

import com.demo.weather.weather.data.FiveDayWeather
import com.demo.weather.weather.helper.WeatherConstants.API_KEY
import com.demo.weather.weather.io.WeatherApi
import javax.inject.Inject

class RemoteWeatherDataSource @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherDataSource {
    override suspend fun getFiveDayWeather(lat: Long, lng: Long): FiveDayWeather {
        val result = weatherApi.getFiveDay(lat.toString(), lng.toString(), API_KEY)
        return result.body()!!
    }
}