package com.demo.weather.map.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.demo.weather.R
import com.demo.weather.common.helper.collectAsEffect
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.hasLocationPermission
import com.demo.weather.databinding.MarkerBinding
import com.demo.weather.location.data.Location
import com.demo.weather.location.viewmodel.LocationViewModel
import com.demo.weather.map.viewmodel.MapViewModel
import com.demo.weather.weather.data.weather.Weather
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.serialization.Serializable

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
    locationViewModel: LocationViewModel = hiltViewModel(),
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val mapView = rememberMapViewWithLifecycle()

    val geocoder = remember { Geocoder(context) }
    var gMap by remember { mutableStateOf<GoogleMap?>(null) }

    locationViewModel.locationState.collectAsEffect { resource ->
        resource.data?.let {
            gMap?.animateToLocation(it)
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize()
    ) { map ->
        map.getMapAsync { googleMap ->
            gMap = googleMap
            if (context.hasLocationPermission()) {
                googleMap.isMyLocationEnabled = true
                locationViewModel.obtainLocation()
            }

            googleMap.setOnCameraIdleListener {
                val centerLocation = googleMap.cameraPosition.target
                mapViewModel.getNearbyWeather(
                    centerLocation.latitude, centerLocation.longitude
                ).collectIn(lifecycleOwner) { resource ->
                    resource.data?.addMarker(
                        googleMap,
                        geocoder,
                        context,
                        onAddMarker = { googleMap.addMarker(it) },
                        isMarkerPresent = { mapViewModel.isMarkerAdded(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(null)
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return mapView
}

fun GoogleMap.animateToLocation(location: Location) {
    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude!!, location.longitude!!), 12f
    )
    this.animateCamera(cameraUpdate)
}

private fun Weather.addMarker(
    googleMap: GoogleMap,
    geocoder: Geocoder,
    context: Context,
    onAddMarker: (MarkerOptions) -> Unit,
    isMarkerPresent: (String) -> Boolean
) {
    if (!isMarkerPresent(station)) {
        val addresses = geocoder.getFromLocationName(station, 1)
        if (addresses?.get(0) != null) {
            val latLng = LatLng(addresses[0].latitude, addresses[0].longitude)
            val mapMarker = MarkerOptions().position(latLng).title(station)
            googleMap.addCustomMarker(temp.toString(), context, mapMarker)
            onAddMarker(mapMarker)
        }
    }
}

private fun GoogleMap.addCustomMarker(
    temp: String,
    context: Context,
    mapMarker: MarkerOptions
) {
    val markerView = MarkerBinding.inflate(LayoutInflater.from(context))
    markerView.root.apply {
        text = context.getString(R.string.current_temp, temp)
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        layout(0, 0, measuredWidth, measuredHeight)
        isDrawingCacheEnabled = true
        buildDrawingCache()
        val markerBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(markerBitmap)
        draw(canvas)
        mapMarker.icon(BitmapDescriptorFactory.fromBitmap(markerBitmap))
        this@addCustomMarker.addMarker(mapMarker)
    }

}

@Serializable
object MapScreenRoute