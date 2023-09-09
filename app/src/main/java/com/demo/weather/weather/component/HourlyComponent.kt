package com.demo.weather.weather.component

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.fadeTo
import com.demo.weather.common.io.ActionableException
import com.demo.weather.databinding.FiveDayComponentBinding
import com.demo.weather.location.data.Location
import com.demo.weather.weather.data.hourly.Weather
import com.demo.weather.weather.viewmodel.HourlyViewModel
import kotlin.math.roundToLong

class HourlyComponent(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: HourlyViewModel,
    private val binding: FiveDayComponentBinding,
    private val updateCurrentWeather: (Weather) -> Unit,
    private val displayError: (ActionableException) -> Unit
) {

    private val weatherAdapter: HourlyListAdapter

    init {
        binding.listview.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            weatherAdapter = HourlyListAdapter()
            adapter = weatherAdapter
        }
    }

    fun getWeatherWithLocation(location: Location) {
        viewModel.getWeather(
            location.latitude!!,
            location.longitude!!
        ).collectIn(lifecycleOwner) { resource ->
            binding.progressBar.fadeTo(Resource.Status.LOADING == resource.status)
            resource.error?.let {
                displayError(it)
            }
            resource.data?.let {
                weatherAdapter.submitList(it)
                binding.listview.fadeTo(true)
                updateCurrentWeather(it[0])
            }
        }
    }
}