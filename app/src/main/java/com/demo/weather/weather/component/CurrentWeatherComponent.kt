package com.demo.weather.weather.component

import androidx.lifecycle.LifecycleOwner
import com.demo.weather.R
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.fadeTo
import com.demo.weather.common.io.ActionableException
import com.demo.weather.databinding.WeatherFragmentBinding
import com.demo.weather.location.data.Location
import com.demo.weather.location.viewmodel.LocationViewModel
import com.demo.weather.weather.data.hourly.Weather

class CurrentWeatherComponent constructor(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: LocationViewModel,
    private val binding: WeatherFragmentBinding,
    private val updateCurrentLocation: (Location) -> Unit,
    private val displayError: (ActionableException) -> Unit
) {

    fun obtainLocation() {
        viewModel.obtainLocation().collectIn(lifecycleOwner) { resource ->
            resource.error?.let {
                displayError(it)
            }
            resource.data?.let {
                updateCurrentLocation(it)
            }
        }
    }

    fun updateWeather(data: Weather) = binding.apply {
        location.fadeTo(true)
        location.text = data.station
        icon.fadeTo(true)
        icon.setImageResource(data.icon ?: R.id.icon)
        temperature.fadeTo(true)
        temperature.text = root.context.getString(R.string.current_temp, data.temp.toString())
        progressBar.fadeTo(false)
    }
}