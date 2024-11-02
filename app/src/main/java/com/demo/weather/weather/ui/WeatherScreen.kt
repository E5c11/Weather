package com.demo.weather.weather.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.demo.weather.R
import com.demo.weather.common.helper.collectAsEffect
import com.demo.weather.common.resources.Dimens
import com.demo.weather.location.viewmodel.LocationViewModel
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.viewmodel.WeatherViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.cancel
import kotlinx.serialization.Serializable

@Composable
fun WeatherScreen(
    locationViewModel: LocationViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    val weatherState = weatherViewModel.weatherState.collectAsState()
    val currentWeather = weatherState.value.data?.firstOrNull()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            locationViewModel.obtainLocation()
        } else {
//            updateError(SafetyObservationUiErrors.PERMISSION_DENIED)
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    locationViewModel.locationState.collectAsEffect {
        it.data?.let { location ->
            weatherViewModel.getWeather(location)
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(Dimens.spacingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CurrentWeatherComponent(
            currentWeather = currentWeather
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(Dimens.dividerWidth)
                .padding(Dimens.spacingMedium),
            thickness = Dimens.dividerThickness
        )

        HourlyWeatherComponent(
            currentWeather = hourlyWeather
        )
    }
}

@Preview
@Composable
fun PreviewWeatherComposable() {
    Weather(
        hourlyWeather = listOf(
            Weather(
                station = "Test Station",
                temp = 29,
                suggestion = "01d",
                latlng = LatLng(0.0, 0.0),
                icon = R.drawable.cloud
            ),
            Weather(
                station = "Test Station",
                temp = 31,
                suggestion = "01d",
                latlng = LatLng(0.0, 0.0),
                icon = R.drawable.cloud
            )
        ),
        currentWeather = Weather(
            station = "Test Station",
            temp = 25,
            suggestion = "01d",
            latlng = LatLng(0.0, 0.0),
            icon = R.drawable.cloud
        )
    )
}

@Serializable
object WeatherScreenRoute