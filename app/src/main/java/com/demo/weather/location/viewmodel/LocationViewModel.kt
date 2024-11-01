package com.demo.weather.location.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.weather.common.helper.Resource
import com.demo.weather.location.data.Location
import com.demo.weather.location.usecase.FetchLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val fetchLocationUseCase: FetchLocationUseCase
): ViewModel() {

    private val _locationState: MutableSharedFlow<Resource<Location>> = MutableStateFlow(Resource.loading())
    val locationState: SharedFlow<Resource<Location>> = _locationState

    fun obtainLocation() = viewModelScope.launch {
        fetchLocationUseCase().collect {
            _locationState.emit(it)
            return@collect
        }
    }
}