package com.demo.weather.weather.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.weather.weather.helper.FiveDayFlow
import com.demo.weather.weather.usecase.FetchFiveDayWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiveDayViewModel @Inject constructor(
    private val fiveDayWeatherUseCase: FetchFiveDayWeatherUseCase
): ViewModel() {

    private var _fiveDayWeatherFlow: FiveDayFlow = emptyFlow()
    val fiveDayWeatherFlow: FiveDayFlow = _fiveDayWeatherFlow

    init {
    }

    fun getWeather(lat: Long, lng: Long) = fiveDayWeatherUseCase(lat, lng)



}