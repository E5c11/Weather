package com.demo.weather.weather.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.demo.weather.R
import com.demo.weather.common.helper.hasLocationPermission
import com.demo.weather.databinding.WeatherFragmentBinding
import com.demo.weather.location.viewmodel.LocationViewModel
import com.demo.weather.weather.component.CurrentWeatherComponent
import com.demo.weather.weather.component.HourlyComponent
import com.demo.weather.weather.data.exception.LocationPermissionDeniedException
import com.demo.weather.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        hourlyComponent = HourlyComponent(
            this, weatherViewModel, binding,
            updateCurrentWeather = {
                currentWeatherComponent.updateWeather(it)

            },
            displayError = {
                findNavController()
                    .navigate(WeatherFragmentDirections.actionGlobalErrorFragment(it))
            }
        )

        currentWeatherComponent = CurrentWeatherComponent(
            this, locationViewModel, binding,
            updateCurrentLocation = {
                hourlyComponent.getWeatherWithLocation(it)
            },
            displayError = {
                findNavController()
                    .navigate(WeatherFragmentDirections.actionGlobalErrorFragment(it))
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

}