package com.demo.weather.weather.component

import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.weather.R
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.fadeTo
import com.demo.weather.common.helper.toCamelCase
import com.demo.weather.common.io.ActionableException
import com.demo.weather.databinding.WeatherFragmentBinding
import com.demo.weather.location.data.Location
import com.demo.weather.location.viewmodel.LocationViewModel
import com.demo.weather.weather.helper.WeatherConstants.STORAGE_URL

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

    fun updateWeather(data: FiveDayWeather) = binding.apply {
        location.fadeTo(true)
        location.text = data.city.name
        icon.fadeTo(true)
        Glide.with(root.context)
            .load("${STORAGE_URL}${data.list[0].description[0].icon}@2x.png")
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(icon)
        temperature.fadeTo(true)
        temperature.text = root.context.getString(
            R.string.current_temp,
            data.list[0].mainWeather.temp.toInt().toString()
        )
        description.fadeTo(true)
        description.text = root.context.getString(
            R.string.current_temp,
            data.list[0].description[0].description.toCamelCase()
        )
        progressBar.fadeTo(false)
    }
}