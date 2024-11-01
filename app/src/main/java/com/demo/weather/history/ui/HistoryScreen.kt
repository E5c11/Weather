package com.demo.weather.history.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.weather.history.viewmodel.HistoryViewModel
import kotlinx.serialization.Serializable

@Composable
fun HistoryScreen(
    viewmodel: HistoryViewModel = hiltViewModel()
) {

}

@Serializable
object HistoryScreenRoute