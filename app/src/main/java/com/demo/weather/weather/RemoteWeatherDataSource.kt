package com.demo.weather.weather

import com.demo.weather.weather.data.FiveDayWeather
import com.demo.weather.weather.io.WeatherApi
import javax.inject.Inject

class RemoteWeatherDataSource @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherDataSource {
    override suspend fun getFiveDayWeather(): FiveDayWeather {
        TODO("Not yet implemented")
    }

}