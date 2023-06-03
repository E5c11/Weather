package com.demo.weather.weather

import com.demo.weather.weather.data.FiveDayWeather

class RemoteWeatherDataSource: WeatherDataSource {
    override suspend fun getFiveDayWeather(): FiveDayWeather {
        TODO("Not yet implemented")
    }

}