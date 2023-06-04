package com.demo.weather.weather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.demo.weather.R
import com.demo.weather.common.helper.hasLocationPermission
import com.demo.weather.databinding.WeatherFragmentBinding
import com.demo.weather.weather.component.CurrentWeatherComponent
import com.demo.weather.weather.component.FiveDayComponent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment: Fragment(R.layout.weather_fragment) {

    @Inject
    lateinit var currentWeatherComponentFactory: CurrentWeatherComponent.CurrentWeatherComponentFactory
    @Inject
    lateinit var fiveDayComponentFactory: FiveDayComponent.FiveDayComponentFactory

    private lateinit var binding: WeatherFragmentBinding

    private lateinit var currentWeatherComponent: CurrentWeatherComponent
    private lateinit var fiveDayComponent: FiveDayComponent

    private val successCode = 0
    private val deniedCode = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WeatherFragmentBinding.bind(view)

        fiveDayComponent = fiveDayComponentFactory.create(
            this, this, binding.fiveDayComponent,
            updateCurrentWeather = {
                currentWeatherComponent.updateWeather(it)
            }
        )

        currentWeatherComponent = currentWeatherComponentFactory.create(
            this, this, binding.currentWeatherComponent,
            updateCurrentLocation = {
                fiveDayComponent.getWeatherWithLocation(it)
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
            else askPermission()
        }

    private fun askPermission() {
        requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

}