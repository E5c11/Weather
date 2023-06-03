package com.demo.weather.weather

import com.demo.weather.weather.data.FiveDayWeather

interface WeatherDataSource {
    suspend fun getFiveDayWeather(): FiveDayWeather
}