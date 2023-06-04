package com.demo.weather.weather.component

import android.location.Location
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.weather.R
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.fadeTo
import com.demo.weather.common.helper.toCamelCase
import com.demo.weather.databinding.CurrentWeatherComponentBinding
import com.demo.weather.weather.data.FiveDayWeather
import com.demo.weather.weather.helper.WeatherConstants.STORAGE_URL
import com.demo.weather.weather.viewmodel.CurrentWeatherViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CurrentWeatherComponent @AssistedInject constructor(
    @Assisted private val lifecycleOwner: LifecycleOwner,
    @Assisted private val storeOwner: ViewModelStoreOwner,
    @Assisted private val binding: CurrentWeatherComponentBinding,
    @Assisted private val updateCurrentLocation: (Location) -> Unit
) {

    private val viewModel: CurrentWeatherViewModel =
        ViewModelProvider(storeOwner)[CurrentWeatherViewModel::class.java]

    fun obtainLocation() {
        viewModel.obtainLocation().collectIn(lifecycleOwner) { resource ->
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

    @AssistedFactory
    interface CurrentWeatherComponentFactory {
        fun create(
            lifecycleOwner: LifecycleOwner,
            storeOwner: ViewModelStoreOwner,
            binding: CurrentWeatherComponentBinding,
            updateCurrentLocation: (Location) -> Unit
        ): CurrentWeatherComponent
    }
}