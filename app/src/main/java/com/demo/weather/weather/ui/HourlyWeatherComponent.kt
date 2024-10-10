package com.demo.weather.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import com.demo.weather.weather.data.weather.Weather

@Composable
fun HourlyWeatherComponent(
    currentWeather: List<Weather>
) {
    Column {
        LazyColumn(
            modifier = Modifier
                .layoutId("listview")
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            val weatherList: List<Weather> = currentWeather ?: emptyList()
            items(items = weatherList) { weather ->
                FiveDayItem(weather)
            }
        }
    }
}