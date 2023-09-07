package com.demo.weather.weather.component

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.fadeTo
import com.demo.weather.common.io.ActionableException
import com.demo.weather.databinding.FiveDayComponentBinding
import com.demo.weather.location.Location
import com.demo.weather.weather.data.FiveDayWeather
import com.demo.weather.weather.viewmodel.FiveDayViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlin.math.roundToLong

class FiveDayComponent @AssistedInject constructor(
    @Assisted private val lifecycleOwner: LifecycleOwner,
    @Assisted private val storeOwner: ViewModelStoreOwner,
    @Assisted private val binding: FiveDayComponentBinding,
    @Assisted private val updateCurrentWeather: (FiveDayWeather) -> Unit,
    @Assisted private val displayError: (ActionableException) -> Unit
) {

    private val viewModel: FiveDayViewModel =
        ViewModelProvider(storeOwner)[FiveDayViewModel::class.java]

    private val weatherAdapter: FiveDayListAdapter

    init {
        binding.listview.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            weatherAdapter = FiveDayListAdapter()
            adapter = weatherAdapter
        }
    }

    fun getWeatherWithLocation(location: Location) {
        viewModel.getWeather(
            (location.latitude!!).roundToLong(),
            (location.longitude!!).roundToLong()
        ).collectIn(lifecycleOwner) { resource ->
            binding.progressBar.fadeTo(Resource.Status.LOADING == resource.status)
            resource.error?.let {
                displayError(it)
            }
            resource.data?.let {
                weatherAdapter.submitList(it.list)
                binding.listview.fadeTo(true)
                updateCurrentWeather(it)
            }
        }
    }

    @AssistedFactory
    interface FiveDayComponentFactory {
        fun create(
            lifecycleOwner: LifecycleOwner,
            storeOwner: ViewModelStoreOwner,
            binding: FiveDayComponentBinding,
            updateCurrentWeather: (FiveDayWeather) -> Unit,
            displayError: (ActionableException) -> Unit
        ): FiveDayComponent
    }
}