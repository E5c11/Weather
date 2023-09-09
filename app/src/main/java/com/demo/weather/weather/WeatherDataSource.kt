package com.demo.weather.weather

interface WeatherDataSource {
    suspend fun getFiveDayWeather(lat: Long, lng: Long): FiveDayWeather
}