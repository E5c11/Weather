package com.demo.weather.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.demo.weather.R
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.helper.WeatherConstants.MATERIAL_ICON_URL
import com.google.android.gms.maps.model.LatLng

@Composable
fun CurrentWeatherComponent(
    currentWeather: Weather?
) {
    Column {
        Image(
            painter = painterResource(id = currentWeather?.icon ?: R.drawable.cloud),
            contentDescription = "Icon",
            modifier = Modifier
                .layoutId("icon")
        )

        Text(
            text = currentWeather?.temp.toString(),
            fontSize = 72.sp,
            modifier = Modifier.layoutId("temperature"),
            color = Color.White
        )

        Text(
            text = "Best for:",
            fontSize = 20.sp,
            modifier = Modifier
                .layoutId("suggestion"),
            color = Color.White
        )

        AsyncImage(
            model = "$MATERIAL_ICON_URL${currentWeather?.suggestion}",
            contentDescription = "Suggestion Image",
            modifier = Modifier
                .layoutId("suggestion_image")
        )
    }
}

@Preview
@Composable
fun PreviewCurrentWeatherComponent() {
    Box(Modifier.background(Color.Blue)) {
        CurrentWeatherComponent(
            currentWeather = Weather(
                station = "Test Station",
                temp = 25,
                suggestion = "01d",
                latlng = LatLng(0.0, 0.0),
                icon = R.drawable.cloud
            )
        )
    }
}