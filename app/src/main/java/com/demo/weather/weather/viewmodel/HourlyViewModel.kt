package com.demo.weather.weather.viewmodel

import androidx.lifecycle.ViewModel
import com.demo.weather.weather.usecase.HourlyWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HourlyViewModel @Inject constructor(
    private val hourlyWeatherUseCase: HourlyWeatherUseCase
): ViewModel() {
    fun getWeather(lat: Double, lng: Double) = hourlyWeatherUseCase(lat, lng)
}