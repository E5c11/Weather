package com.demo.weather.common.ui.components

import androidx.lifecycle.LifecycleOwner
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.fadeTo
import com.demo.weather.databinding.LoadingComponentBinding
import kotlinx.coroutines.flow.Flow

class LoadingComponent(
    lifecycleOwner: LifecycleOwner,
    private val binding: LoadingComponentBinding
): BaseComponent<Resource<String>>(lifecycleOwner) {

    override fun collect(visibilityFlow: Flow<Boolean>, dataFlow: Flow<Resource<String>>) {
        visibilityFlow.collectIn(owner) {
            binding.root.fadeTo(it)
        }
    }

}