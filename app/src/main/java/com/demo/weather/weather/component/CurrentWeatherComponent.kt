package com.demo.weather.weather.component

import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.demo.weather.R
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.fadeTo
import com.demo.weather.common.ui.components.BaseComponent
import com.demo.weather.databinding.WeatherFragmentBinding
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.helper.WeatherConstants.MATERIAL_ICON_URL
import kotlinx.coroutines.flow.Flow

class CurrentWeatherComponent(
    lifecycleOwner: LifecycleOwner,
    private val binding: WeatherFragmentBinding,
): BaseComponent<Resource<Weather>>(lifecycleOwner) {

    override fun collect(visibilityFlow: Flow<Boolean>, dataFlow: Flow<Resource<Weather>>) {
        visibilityFlow.collectIn(owner) {
            binding.apply {
                location.fadeTo(it)
                icon.fadeTo(it)
                temperature.fadeTo(it)
            }
        }

        dataFlow.collectIn(owner) { resource ->
            resource.data?.let {
                binding.apply {
                    location.text = it.station
                    icon.setImageResource(it.icon ?: R.id.icon)
                    temperature.text = root.context.getString(R.string.current_temp, it.temp.toString())
                    suggestion.text = root.context.getString(R.string.best_for)
                    suggestionImage.loadUrl("${MATERIAL_ICON_URL}${it.suggestion}")
                }
            }
        }
    }

    // Credit https://stackoverflow.com/a/74357994/6333708
    private fun ImageView.loadUrl(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
//            .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
            .build()
        val request = ImageRequest.Builder(this.context)
            .data(url)
            .target(this)
            .build()
        imageLoader.enqueue(request)
    }

}