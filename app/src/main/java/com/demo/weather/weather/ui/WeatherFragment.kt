package com.demo.weather.weather.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.demo.weather.R
import com.demo.weather.databinding.WeatherFragmentBinding
import com.demo.weather.weather.component.FiveDayComponent
import javax.inject.Inject

class WeatherFragment: Fragment(R.layout.weather_fragment) {

    private lateinit var binding: WeatherFragmentBinding

    @Inject lateinit var fiveDayComponent: FiveDayComponent

    lateinit var fiveDayComponentFactory: FiveDayComponent.FiveDayComponentFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WeatherFragmentBinding.bind(view)

        fiveDayComponent = fiveDayComponentFactory.create(
            this, this, binding.fiveDayComponent
        )
    }


}