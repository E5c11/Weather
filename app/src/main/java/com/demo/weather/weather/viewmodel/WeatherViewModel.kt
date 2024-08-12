package com.demo.weather.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.io.ActionableException
import com.demo.weather.location.data.Location
import com.demo.weather.location.data.exception.LocationNotFoundException
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.usecase.HourlyWeatherUseCase
import com.demo.weather.weather.usecase.SaveWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val hourlyWeatherUseCase: HourlyWeatherUseCase,
    private val saveWeatherUseCase: SaveWeatherUseCase
): ViewModel() {

    private val _weatherState: MutableStateFlow<Resource<List<Weather>>> = MutableStateFlow(Resource.loading())
    val weatherState: StateFlow<Resource<List<Weather>>> = _weatherState

    fun getWeather(location: Location) = viewModelScope.launch {
        location.let {
            if (it.latitude != null && it.longitude != null) {
                hourlyWeatherUseCase(it.latitude, it.longitude).collect { resource ->
                    _weatherState.value = resource
                    resource.data?.let { weather ->
                        saveWeather(weather)
                    }
                }
            } else {
                _weatherState.value = Resource.error(LocationNotFoundException())
            }
        }
    }

    private fun saveWeather(weather: List<Weather>) = viewModelScope.launch {
        saveWeatherUseCase(weather)
    }
}