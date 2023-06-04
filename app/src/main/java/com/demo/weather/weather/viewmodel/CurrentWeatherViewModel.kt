package com.demo.weather.weather.viewmodel

import androidx.lifecycle.ViewModel
import com.demo.weather.weather.usecase.FetchLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val fetchLocationUseCase: FetchLocationUseCase
): ViewModel() {
    fun obtainLocation() = fetchLocationUseCase()
}