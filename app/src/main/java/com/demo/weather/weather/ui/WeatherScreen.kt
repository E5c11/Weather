package com.demo.weather.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.weather.location.viewmodel.LocationViewModel
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.viewmodel.WeatherViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.cancel

@Composable
fun WeatherScreen(
    locationViewModel: LocationViewModel,
    weatherViewModel: WeatherViewModel
) {
    val weatherState = weatherViewModel.weatherState.collectAsState()
    val currentWeather = weatherState.value.data?.firstOrNull()

    LaunchedEffect(Unit) {
        locationViewModel.locationState.collect {
           it.data?.let { location ->
               weatherViewModel.getWeather(location)
               cancel()
           }
        }
    }

    Weather(
        hourlyWeather = weatherState.value.data?.drop(1),
        currentWeather = currentWeather
    )
}

@Composable
fun Weather(
    hourlyWeather: List<Weather>?,
    currentWeather: Weather?
) {
    Column {
        CurrentWeatherComponent(
            currentWeather = currentWeather
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp)
                .layoutId("divider"),
            thickness = 1.dp
        )

        HourlyWeatherComponent(
            currentWeather = hourlyWeather
        )
    }
}

@Preview
@Composable
fun PreviewWeatherComposable() {
    Box(Modifier.background(Color.White)) {
        Weather(
            hourlyWeather = listOf(
                Weather(
                    station = "Test Station",
                    temp = 25,
                    suggestion = "01d",
                    latlng = LatLng(0.0, 0.0),
                    icon = 0
                )
            ),
            currentWeather = Weather(
                station = "Test Station",
                temp = 25,
                suggestion = "01d",
                latlng = LatLng(0.0, 0.0),
                icon = 0
            )
        )
    }
}