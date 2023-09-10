package com.demo.weather.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.weather.common.io.ActionableException
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.usecase.HourlyWeatherUseCase
import com.demo.weather.weather.usecase.SaveWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val hourlyWeatherUseCase: HourlyWeatherUseCase,
    private val saveWeatherUseCase: SaveWeatherUseCase
): ViewModel() {

    fun getWeather(lat: Double, lng: Double) = hourlyWeatherUseCase(lat, lng)

    fun saveWeather(weather: List<Weather>) = viewModelScope.launch {
        saveWeatherUseCase(weather)
    }
}