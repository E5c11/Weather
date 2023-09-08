package com.demo.weather.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.demo.weather.R
import com.demo.weather.common.helper.collectIn
import com.demo.weather.databinding.MapFragmentBinding
import com.demo.weather.location.data.Location
import com.demo.weather.location.viewmodel.LocationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment: Fragment(R.layout.map_fragment), OnMapReadyCallback {

    private val viewModel: LocationViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var binding: MapFragmentBinding
    private lateinit var mapIcon: MarkerOptions
    private lateinit var location: Location

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MapFragmentBinding.bind(view)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)

        getLocation()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.isMyLocationEnabled = true

        map.setOnMapClickListener {
            map.clear()
            mapIcon = MarkerOptions().position(it).title("My Location")
            map.addMarker(mapIcon)
        }
        if (this::location.isInitialized) location.animateToLocation()
    }

    private fun Location.animateToLocation() {
        if (this.latitude != null && this.longitude != null) {
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                LatLng(this.latitude, this.longitude), 13f
            )
            map.animateCamera(cameraUpdate)
        }
    }

    private fun getLocation() {
        val loc = MapFragmentArgs.fromBundle(requireArguments()).location
        if (loc == null) {
            viewModel.obtainLocation().collectIn(viewLifecycleOwner) { resource ->
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