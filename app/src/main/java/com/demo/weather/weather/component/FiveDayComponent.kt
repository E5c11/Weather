package com.demo.weather.weather.component

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.helper.collectIn
import com.demo.weather.databinding.FiveDayComponentBinding
import com.demo.weather.weather.data.MainWeather
import com.demo.weather.weather.viewmodel.FiveDayViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.demo.weather.common.helper.fadeTo

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
        binding.listview.adapter = adapter
        viewModel.getWeather(18.487599.toLong(), (-33.958240).toLong()).collectIn(lifecycleOwner) { resource ->
            binding.listview.fadeTo(Resource.Status.LOADING != resource.status)
            Log.d("myT", "component: $resource")
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
            binding: FiveDayComponentBinding,
            updateCurrentWeather: (MainWeather) -> Unit
        ): FiveDayComponent
    }
}