package com.demo.weather

import com.demo.weather.data.FiveDayWeather

class RemoteWeatherDataSource: WeatherDataSource {
    override suspend fun getFiveDayWeather(): FiveDayWeather {
        TODO("Not yet implemented")
    }

}