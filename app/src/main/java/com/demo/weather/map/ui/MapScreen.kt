package com.demo.weather.map.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.weather.map.viewmodel.MapViewModel
import kotlinx.serialization.Serializable

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {

}

@Serializable
object MapScreenRoute