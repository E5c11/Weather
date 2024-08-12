package com.demo.weather.weather.component

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.fadeTo
import com.demo.weather.common.io.ActionableException
import com.demo.weather.common.ui.components.BaseComponent
import com.demo.weather.databinding.WeatherFragmentBinding
import com.demo.weather.location.data.Location
import com.demo.weather.weather.data.weather.Weather
import kotlinx.coroutines.flow.Flow

class HourlyComponent(
    lifecycleOwner: LifecycleOwner,
    private val binding: WeatherFragmentBinding,
    private val updateLocation: (Location) -> Unit
): BaseComponent<Resource<List<Weather>>>(lifecycleOwner) {

    private val weatherAdapter: HourlyListAdapter

    init {
        binding.listview.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            weatherAdapter = HourlyListAdapter()
            adapter = weatherAdapter
        }
    }

    override fun collect(visibilityFlow: Flow<Boolean>, dataFlow: Flow<Resource<List<Weather>>>) {
        visibilityFlow.collectIn(owner) {
            binding.listview.fadeTo(it)
        }

        dataFlow.collectIn(owner) { resource ->
            resource.data?.let {
                weatherAdapter.submitList(it)
            }
        }
    }

    fun collectLocation(dataFlow: Flow<Resource<Location?>>) {
        dataFlow.collectIn(owner) { resource ->
            resource.data?.let {
                updateLocation(it)
            }
        }
    }
}