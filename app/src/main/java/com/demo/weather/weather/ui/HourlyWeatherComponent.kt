package com.demo.weather.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.weather.R
import com.demo.weather.weather.data.weather.Weather
import com.google.android.gms.maps.model.LatLng

@Composable
fun HourlyWeatherComponent(
    currentWeather: List<Weather>?
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

@Composable
fun FiveDayItem(weather: Weather?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = weather?.icon ?: R.drawable.cloud),
            contentDescription = stringResource(id = R.string.icon_description),
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp)
        )

        Text(
            text = weather?.time.toString(),
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .padding(end = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.day_weather, weather?.temp.toString(), weather?.wind.toString()),
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun FiveDayItemPreview() {
    Box(Modifier.background(Color.Blue)) {
        FiveDayItem(
            weather = Weather(
                time = "12:00",
                temp = 25,
                wind = 10,
                icon = R.drawable.cloud,
                suggestion = "01d",
                latlng = LatLng(0.0, 0.0),
                station = "Test Station"
            )
        )
    }
}