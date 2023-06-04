package com.demo.weather.weather.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.demo.weather.R
import com.demo.weather.databinding.WeatherFragmentBinding
import com.demo.weather.weather.component.FiveDayComponent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment: Fragment(R.layout.weather_fragment) {

    @Inject lateinit var fiveDayComponentFactory: FiveDayComponent.FiveDayComponentFactory

    private lateinit var binding: WeatherFragmentBinding

    private lateinit var fiveDayComponent: FiveDayComponent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WeatherFragmentBinding.bind(view)

        fiveDayComponent = fiveDayComponentFactory.create(
            this, this, binding.fiveDayComponent,
            updateCurrentWeather = {

            }
        )
    }


}