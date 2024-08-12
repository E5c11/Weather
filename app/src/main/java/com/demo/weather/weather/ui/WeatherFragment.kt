package com.demo.weather.weather.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.demo.weather.R
import com.demo.weather.common.helper.Constant.ERROR
import com.demo.weather.common.helper.Constant.LOADING
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.helper.StringFlow
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.hasLocationPermission
import com.demo.weather.common.helper.isError
import com.demo.weather.common.helper.isLoading
import com.demo.weather.common.helper.isSuccess
import com.demo.weather.common.io.ActionableException
import com.demo.weather.common.ui.components.LoadingComponent
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

const val MOTION_PROGRESS = "motion_progress"
const val RECYCLER_STATE = "recycler_state"

@AndroidEntryPoint
class WeatherFragment: Fragment(R.layout.weather_fragment) {

    private lateinit var binding: WeatherFragmentBinding

    private val locationViewModel: LocationViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WeatherFragmentBinding.bind(view)

        if (savedInstanceState != null) {
            val state = savedInstanceState.getParcelable<Parcelable>(RECYCLER_STATE)
            binding.listview.layoutManager?.onRestoreInstanceState(state)
            val progress = savedInstanceState.getFloat(MOTION_PROGRESS)
            binding.root.progress = progress
        }

        requireContext().isLocationEnabled()

        requestLocation()
        setupComponents()
        transitionalStateComponents()
    }

    private fun transitionalStateComponents() {
        val errorState: StringFlow = merge(
            weatherViewModel.weatherState.map { Resource(it.status, ERROR, it.error) },
            locationViewModel.locationState.map { Resource(it.status, ERROR, it.error) }
        ).filter { it.isError() }

        val loadingState: StringFlow = merge(
            weatherViewModel.weatherState.map { Resource(it.status, LOADING, it.error) }
        )

        LoadingComponent(
            this,
            binding.loadingComponent
        ).collect(loadingState.map { it.isLoading() }, loadingState)

        errorState.collectIn(this) {
            it.error?.showError()
        }
    }

    private fun setupComponents() {
        HourlyComponent(
            this,
            binding,
            updateLocation = {
                weatherViewModel.getWeather(it)
            }
        ).also { component ->
            component.collectLocation(locationViewModel.locationState)
            component.collect(
                weatherViewModel.weatherState.map { it.isSuccess() },
                weatherViewModel.weatherState
            )
        }

        CurrentWeatherComponent(
            this,
            binding
        ).collect(
            weatherViewModel.weatherState.map { it.isSuccess() },
            weatherViewModel.weatherState.map {
                Resource(
                    it.status,
                    it.data?.firstOrNull(),
                    it.error
                )
            }
        )
    }

    private fun requestLocation() {
        if (requireContext().hasLocationPermission()) locationViewModel.obtainLocation()
        else askPermission()
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) locationViewModel.obtainLocation()
            else LocationPermissionDeniedException().showError()
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
        if (result.resultCode == Activity.RESULT_OK) requestLocation()
        else ActionableException("Please make sure location services are enabled").showError()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val layoutManager = binding.listview.layoutManager
        val state = layoutManager?.onSaveInstanceState()
        outState.putParcelable(RECYCLER_STATE, state)
        outState.putFloat(MOTION_PROGRESS, binding.root.progress)
    }

    private fun ActionableException.showError() =
        findNavController().navigate(WeatherFragmentDirections.actionGlobalErrorFragment(this))

}