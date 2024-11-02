package com.demo.weather.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.AsyncImage
import com.demo.weather.R
import com.demo.weather.common.resources.Dimens
import com.demo.weather.common.ui.composables.TitleText
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.helper.WeatherConstants.MATERIAL_ICON_URL
import com.google.android.gms.maps.model.LatLng

@Composable
fun CurrentWeatherComponent(
    currentWeather: Weather?
) {
    if (currentWeather?.temp == null) {
        CircularProgressIndicator(modifier = Modifier.padding(Dimens.spacingMedium))
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleText(
                text = currentWeather.station,
                color = Color.White,
                modifier = Modifier.padding(Dimens.spacingMedium)
            )
            Image(
                painter = painterResource(id = currentWeather.icon ?: R.drawable.cloud),
                contentDescription = "Icon",
            )

            Text(
                text = currentWeather.temp.toString(),
                fontSize = Dimens.textSizeMega,
                color = Color.White
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleText(
                    text = stringResource(R.string.weather_best_for),
                    color = Color.White
                )

                AsyncImage(
                    model = "$MATERIAL_ICON_URL${currentWeather.suggestion}",
                    contentDescription = "Suggestion Image",
                    modifier = Modifier
                        .size(Dimens.imageSizeMedium)
                )
            }
        }
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