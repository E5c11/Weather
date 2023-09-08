package com.demo.weather.location.viewmodel

import androidx.lifecycle.ViewModel
import com.demo.weather.location.data.Location
import com.demo.weather.weather.usecase.FetchLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val fetchLocationUseCase: FetchLocationUseCase
): ViewModel() {
    private var _location: Location? = null
    fun getLocation() = _location

    fun obtainLocation() = fetchLocationUseCase().map {
        _location = it.data
        it
    }
}