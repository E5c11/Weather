package com.demo.weather.weather.ui

import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import coil.compose.AsyncImage
import com.demo.weather.R
import com.demo.weather.location.viewmodel.LocationViewModel
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.helper.WeatherConstants.MATERIAL_ICON_URL
import com.demo.weather.weather.viewmodel.WeatherViewModel
import kotlinx.coroutines.cancel

@OptIn(ExperimentalMotionApi::class, ExperimentalFoundationApi::class)
@Composable
fun WeatherComposable(
    locationViewModel: LocationViewModel,
    weatherViewModel: WeatherViewModel
) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.weather_motion_scene)
            .readBytes()
            .decodeToString()
    }
    val weatherState = weatherViewModel.weatherState.collectAsState()
    val currentWeather = weatherState.value.data?.firstOrNull()

    val draggedDownAnchorTop = with(LocalDensity.current) { 200.dp.toPx() }
    val anchors = DraggableAnchors {
        AnchoredDraggableCardState.DRAGGED_DOWN at draggedDownAnchorTop
        AnchoredDraggableCardState.DRAGGED_UP at 0f
    }
    val decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay()
    val density = LocalDensity.current
    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = AnchoredDraggableCardState.DRAGGED_DOWN,
            anchors = anchors,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimationSpec
        )
    }

    val offset = if (anchoredDraggableState.offset.isNaN()) 0f else anchoredDraggableState.offset
    val progress = (1 - (offset / draggedDownAnchorTop)).coerceIn(0f, 1f)

    LaunchedEffect(Unit) {
        locationViewModel.locationState.collect {
           it.data?.let { location ->
               weatherViewModel.getWeather(location)
               cancel()
           }
        }
    }

    MotionLayout(
        motionScene = MotionScene(motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
//            .paint(
//                painterResource(id = R.drawable.simple_gradient),
//                contentScale = ContentScale.FillBounds
//            )
    ) {
        Text(
            text = currentWeather?.station ?: "Location",
            fontSize = 36.sp,
            modifier = Modifier
                .layoutId("location")
                .padding(top = 16.dp),
            color = Color.White
        )

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

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp)
                .layoutId("divider"),
            thickness = 1.dp
        )

        LazyColumn(
            modifier = Modifier
                .layoutId("listview")
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .anchoredDraggable(
                    state = anchoredDraggableState,
                    orientation = Orientation.Vertical
                )
        ) {
            val weatherList: List<Weather> = weatherState.value.data ?: emptyList()
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
fun PreviewWeatherComposable() {
//    WeatherComposable()
}

enum class AnchoredDraggableCardState {
    DRAGGED_DOWN, DRAGGED_UP
}
