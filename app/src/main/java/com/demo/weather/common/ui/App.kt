package com.demo.weather.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.weather.common.resources.WeatherTheme
import com.demo.weather.common.ui.composables.WeatherBottomBar
import com.demo.weather.history.ui.HistoryScreen
import com.demo.weather.history.ui.HistoryScreenRoute
import com.demo.weather.map.ui.MapScreen
import com.demo.weather.map.ui.MapScreenRoute
import com.demo.weather.weather.ui.WeatherScreen
import com.demo.weather.weather.ui.WeatherScreenRoute

@Composable
fun App() {

    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf<Any>(WeatherScreenRoute) }

    WeatherTheme {
        Scaffold(
            modifier = Modifier,
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WeatherBottomBar(
                        navController = navController,
                        selectedItem = selectedItem
                    ) { selected ->
                        selectedItem = selected
                    }
                }
            }
        ) { padding ->

            Column(
                modifier = Modifier.padding(padding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = WeatherScreenRoute
                ) {
                    composable<WeatherScreenRoute> {
                        WeatherScreen()
                    }
                    composable<HistoryScreenRoute> {
                        HistoryScreen()
                    }
                    composable<MapScreenRoute> {
                        MapScreen()
                    }
                }
            }
        }
    }
}