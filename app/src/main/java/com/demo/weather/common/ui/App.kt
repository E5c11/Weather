package com.demo.weather.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.demo.weather.common.resources.WeatherTheme
import com.demo.weather.weather.ui.WeatherScreenRoute

@Composable
fun App() {
    WeatherTheme {
        Scaffold(
            modifier = Modifier,
            bottomBar = {

            }
        ) { padding ->
            val navController = rememberNavController()

            Column(
                modifier = Modifier.padding(padding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = WeatherScreenRoute
                ) {

                }
            }
        }
    }
}