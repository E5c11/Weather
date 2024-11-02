package com.demo.weather.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.demo.weather.R
import com.demo.weather.common.resources.Dimens
import com.demo.weather.common.ui.composables.BodyText
import com.demo.weather.weather.data.weather.Weather
import com.google.android.gms.maps.model.LatLng

@Composable
fun HourlyWeatherComponent(
    currentWeather: List<Weather>?
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        if (currentWeather.isNullOrEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn {
                val weatherList: List<Weather> = currentWeather
                items(items = weatherList) { weather ->
                    FiveDayItem(weather)
                }
            }
        }
    }
}

@Composable
fun FiveDayItem(weather: Weather?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.columnItemHeightMedium)
            .padding(horizontal = Dimens.spacingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = weather?.icon ?: R.drawable.cloud),
            contentDescription = stringResource(id = R.string.icon_description),
            modifier = Modifier
                .size(Dimens.imageSizeLarge)
                .padding(end = Dimens.spacingMedium)
        )

        BodyText(
            text = weather?.time.toString(),
            color = Color.White,
            modifier = Modifier
                .padding(end = Dimens.spacingMedium)
        )

        Spacer(modifier = Modifier.weight(1f))
        BodyText(
            text = stringResource(
                R.string.day_weather,
                weather?.temp.toString(),
                weather?.wind.toString()
            ),
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