package com.demo.weather

import com.demo.weather.data.FiveDayWeather

interface WeatherDataSource {
    suspend fun getFiveDayWeather(): FiveDayWeather
}