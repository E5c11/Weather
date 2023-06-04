package com.demo.weather.weather.component

import android.location.Location
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.demo.weather.common.helper.collectIn
import com.demo.weather.databinding.FiveDayComponentBinding
import com.demo.weather.weather.data.MainWeather
import com.demo.weather.weather.viewmodel.FiveDayViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class FiveDayComponent @AssistedInject constructor(
    @Assisted private val lifecycleOwner: LifecycleOwner,
    @Assisted private val storeOwner: ViewModelStoreOwner,
    @Assisted private val binding: FiveDayComponentBinding,
    @Assisted private val updateCurrentWeather: (MainWeather) -> Unit
) {

    private val viewModel: FiveDayViewModel =
        ViewModelProvider(storeOwner)[FiveDayViewModel::class.java]

    private val adapter = FiveDayListAdapter()

    init {
        viewModel.getWeather(18.487599.toLong(), (-33.958240).toLong())
        viewModel.fiveDayWeatherFlow.collectIn(lifecycleOwner) { resource ->
            resource.data?.let {
                adapter.submitList(it.list)
                updateCurrentWeather(it.list[0].mainWeather)
            }
        }
    }

    fun getWeatherWithLocation() {
        viewModel.getWeather(18.487599.toLong(), (-33.958240).toLong())
    }

    @AssistedFactory
    interface FiveDayComponentFactory {
        fun create(
            lifecycleOwner: LifecycleOwner,
            storeOwner: ViewModelStoreOwner,
            binding: FiveDayComponentBinding
        ): FiveDayComponent
    }
}