package com.demo.weather.history.ui

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.demo.weather.R
import com.demo.weather.common.helper.toDayOfTheWeek
import com.demo.weather.common.resources.Dimens
import com.demo.weather.common.ui.composables.BodyText
import com.demo.weather.common.ui.composables.TitleText
import com.demo.weather.history.viewmodel.HistoryViewModel
import com.demo.weather.weather.data.weather.Weather
import kotlinx.serialization.Serializable

@Composable
fun HistoryScreen(
    viewmodel: HistoryViewModel = hiltViewModel()
) {

    val state = viewmodel.state.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                viewmodel.getRecentLocationsWeather()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (state.value.isNullOrEmpty()) {
        CircularProgressIndicator()
    } else {
        LazyColumn {
            items(items = state.value!!) { weather ->
                HistoryListItem(weather = weather)
            }
        }
    }
}

@Composable
fun HistoryListItem(
    weather: Weather
) {
    Column(
        modifier = Modifier.padding(Dimens.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
    ) {
        TitleText(text = weather.station)
        Row(
            modifier = Modifier
                .padding(
                    start = Dimens.spacingMedium,
                    end = Dimens.spacingMedium,
                ),
        ) {
            Icon(
                painter = painterResource(weather.icon ?: R.drawable.cloud),
                contentDescription = "Weather symbol"
            )
            Spacer(modifier = Modifier.weight(1f))
            BodyText(text = weather.time?.toDayOfTheWeek() ?: "")
            Spacer(modifier = Modifier.weight(1f))
            BodyText(
                text = stringResource(
                    R.string.day_weather,
                    weather.temp.toString(),
                    weather.wind.toString()
                )
            )
        }
        HorizontalDivider(
            modifier = Modifier.weight(Dimens.dividerWidth)
        )
    }
}

@Serializable
object HistoryScreenRoute