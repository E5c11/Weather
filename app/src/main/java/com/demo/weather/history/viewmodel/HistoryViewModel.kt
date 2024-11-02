package com.demo.weather.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.weather.common.helper.Resource
import com.demo.weather.history.usecase.FetchRecentLocationsWeatherUseCase
import com.demo.weather.weather.data.weather.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val fetchRecentLocationsWeatherUseCase: FetchRecentLocationsWeatherUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<List<Weather>?> = MutableStateFlow(null)
    val state: StateFlow<List<Weather>?> = _state

    fun getRecentLocationsWeather() = viewModelScope.launch {
        fetchRecentLocationsWeatherUseCase().collect { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> _state.update { resource.data }
                Resource.Status.ERROR -> _state.update { emptyList() }
                Resource.Status.LOADING -> {
                    // Handle loading
                }
            }
        }
    }
}