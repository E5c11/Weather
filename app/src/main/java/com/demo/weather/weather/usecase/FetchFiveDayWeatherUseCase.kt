package com.demo.weather.weather.usecase

import com.demo.weather.weather.WeatherRepository
import javax.inject.Inject

class FetchFiveDayWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(lat: Long, lng: Long) = repository.fetchClosest(lat, lng)
}