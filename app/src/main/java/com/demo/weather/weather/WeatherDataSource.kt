package com.demo.weather.weather

import com.demo.weather.weather.data.entity.FiveDayWeather

interface WeatherDataSource {
    suspend fun getFiveDayWeather(lat: Long, lng: Long): FiveDayWeather
}