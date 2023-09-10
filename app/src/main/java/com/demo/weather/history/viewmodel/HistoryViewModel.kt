package com.demo.weather.history.viewmodel

import androidx.lifecycle.ViewModel
import com.demo.weather.history.usecase.FetchRecentLocationsWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val fetchRecentLocationsWeatherUseCase: FetchRecentLocationsWeatherUseCase
) : ViewModel() {

    fun getRecentLocationsWeather() = fetchRecentLocationsWeatherUseCase()
}