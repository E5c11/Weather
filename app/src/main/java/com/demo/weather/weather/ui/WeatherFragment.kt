package com.demo.weather.weather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.demo.weather.R
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.hasLocationPermission
import com.demo.weather.common.io.ActionableException
import com.demo.weather.databinding.WeatherFragmentBinding
import com.demo.weather.location.viewmodel.LocationViewModel
import com.demo.weather.weather.component.CurrentWeatherComponent
import com.demo.weather.weather.component.HourlyComponent
import com.demo.weather.weather.data.exception.LocationPermissionDeniedException
import com.demo.weather.weather.viewmodel.WeatherViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment: Fragment(R.layout.weather_fragment) {

    private lateinit var binding: WeatherFragmentBinding

    private lateinit var currentWeatherComponent: CurrentWeatherComponent
    private lateinit var hourlyComponent: HourlyComponent

    private val locationViewModel: LocationViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WeatherFragmentBinding.bind(view)

        requireContext().isLocationEnabled()

        hourlyComponent = HourlyComponent(
            this, weatherViewModel, binding,
            updateCurrentWeather = {
                currentWeatherComponent.updateWeather(it)

            },
            displayError = {
                it.showError()
            }
        )

        currentWeatherComponent = CurrentWeatherComponent(
            this, locationViewModel, binding,
            updateCurrentLocation = {
                hourlyComponent.getWeatherWithLocation(it)
            },
            displayError = {
                it.showError()
            }
        )
        requestLocation()
    }

    private fun requestLocation() {
        if (requireContext().hasLocationPermission()) currentWeatherComponent.obtainLocation()
        else askPermission()
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) currentWeatherComponent.obtainLocation()
            else findNavController()
                .navigate(
                    WeatherFragmentDirections.actionGlobalErrorFragment(
                        LocationPermissionDeniedException()
                    )
                )
        }

    private fun askPermission() {
        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun Context.isLocationEnabled() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100).build()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(this)
        val locationSettingsResponseTask =
            settingsClient.checkLocationSettings(builder.build())
        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                try {
                    locationSettingsLauncher.launch(
                        IntentSenderRequest.Builder(exception.resolution).build()
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    ActionableException("Error getting location settings resolution: ${sendEx.message}").showError()
                }
            }
        }
        locationSettingsResponseTask.addOnCompleteListener {
            if ( it.isSuccessful ) {
                requestLocation()
            }
        }
    }

    private val locationSettingsLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            requestLocation()
        } else {
            ActionableException("Please make sure location services are enabled").showError()
        }
    }

    private fun ActionableException.showError() =
        findNavController().navigate(WeatherFragmentDirections.actionGlobalErrorFragment(this))

}