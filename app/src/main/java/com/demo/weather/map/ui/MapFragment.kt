package com.demo.weather.map.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.demo.weather.R
import com.demo.weather.common.helper.collectIn
import com.demo.weather.databinding.MapFragmentBinding
import com.demo.weather.databinding.MarkerBinding
import com.demo.weather.location.data.Location
import com.demo.weather.location.viewmodel.LocationViewModel
import com.demo.weather.map.viewmodel.MapViewModel
import com.demo.weather.weather.data.weather.Weather
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment: Fragment(R.layout.map_fragment), OnMapReadyCallback {

    private val locationViewModel: LocationViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var binding: MapFragmentBinding
    private lateinit var location: Location
    private lateinit var geocoder: Geocoder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MapFragmentBinding.bind(view)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)

        geocoder = Geocoder(requireContext())

        getLocation()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getNearbyWeather()
        map.isMyLocationEnabled = true

        if (this::location.isInitialized) location.animateToLocation()
    }

    private fun getNearbyWeather() {
        map.setOnCameraIdleListener {
            val centerLocation = map.cameraPosition.target
            mapViewModel.getNearbyWeather(
                centerLocation.latitude, centerLocation.longitude
            ).collectIn(viewLifecycleOwner) { resource ->
                resource.data?.addMarker()
            }
        }
    }

    private fun Weather.addMarker() {
        val isMarkerPresent = mapViewModel.isMarkerAdded(station)
        if (!isMarkerPresent) {
            val addresses = geocoder.getFromLocationName(station, 1)
            if (addresses?.get(0) != null) {
                val latLng = LatLng(addresses[0].latitude, addresses[0].longitude)
                val mapMarker =
                    MarkerOptions().position(latLng).title(station)
                createMakerWithText(temp.toString(), mapMarker)
            }
        }
    }

    private fun createMakerWithText(temp: String, mapMarker: MarkerOptions) {
        val markerView = MarkerBinding.inflate(layoutInflater)
        markerView.root.apply {
            text = getString(R.string.current_temp, temp)
            measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            layout(0, 0, measuredWidth, measuredHeight)
            isDrawingCacheEnabled = true
            buildDrawingCache()
            val markerBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(markerBitmap)
            draw(canvas)
            mapMarker.icon(BitmapDescriptorFactory.fromBitmap(markerBitmap))
            map.addMarker(mapMarker)
            mapViewModel.addMarker(mapMarker)
        }
    }

    private fun Location.animateToLocation() {
        if (this.latitude != null && this.longitude != null) {
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                LatLng(this.latitude, this.longitude), 12f
            )
            map.animateCamera(cameraUpdate)
        }
    }

    private fun getLocation() {
        val loc = MapFragmentArgs.fromBundle(requireArguments()).location
        if (loc == null) {
            locationViewModel.obtainLocation().collectIn(viewLifecycleOwner) { resource ->
                resource.data?.animateToLocation()
            }
        } else location = Location(loc.latitude, loc.longitude, loc.accuracy)
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }
    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }
    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }
    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }


}