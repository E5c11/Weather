package com.demo.weather.weather.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.weather.weather.helper.FiveDayFlow
import com.demo.weather.weather.usecase.FetchFiveDayWeatherUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FiveDayViewModel @Inject constructor(
    private val fiveDayWeatherUseCase: FetchFiveDayWeatherUseCase
): ViewModel() {

    private lateinit var _fiveDayWeatherFlow: FiveDayFlow
    val fiveDayWeatherFlow: FiveDayFlow = _fiveDayWeatherFlow

    fun getWeather(lat: Long, lng: Long) = viewModelScope.launch {
        _fiveDayWeatherFlow = fiveDayWeatherUseCase(lat, lng)
    }



}