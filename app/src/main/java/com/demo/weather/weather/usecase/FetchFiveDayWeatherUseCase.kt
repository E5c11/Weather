package com.demo.weather.weather.usecase

import android.location.Location
import com.demo.weather.weather.WeatherRepository
import javax.inject.Inject

class FetchFiveDayWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(lat: Long, lng: Long) = repository.fetch(lat, lng)
}