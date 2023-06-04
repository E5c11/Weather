package com.demo.weather.weather.viewmodel

import androidx.lifecycle.ViewModel
import com.demo.weather.weather.usecase.FetchFiveDayWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FiveDayViewModel @Inject constructor(
    private val fiveDayWeatherUseCase: FetchFiveDayWeatherUseCase
): ViewModel() {
    fun getWeather(lat: Long, lng: Long) = fiveDayWeatherUseCase(lat, lng)
}