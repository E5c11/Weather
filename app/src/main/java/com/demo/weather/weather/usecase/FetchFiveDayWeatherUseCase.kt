package com.demo.weather.weather.usecase

import android.location.Location
import android.util.Log
import com.demo.weather.weather.WeatherRepository
import com.demo.weather.weather.helper.FiveDayFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.log

class FetchFiveDayWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(lat: Long, lng: Long) = repository.fetch(lat, lng)
}