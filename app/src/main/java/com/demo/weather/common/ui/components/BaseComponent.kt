package com.demo.weather.common.ui.components

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow

/**
 * Self contained & reusable ui component.This is a lifecycle aware component.
 * It makes sure we don't clutter an android Fragment/Activity with ui logic.
 */
abstract class BaseComponent<T>(val owner: LifecycleOwner) {
    private val lifecycleObserver = ComponentLifecycleObserver()

    init {
        owner.lifecycle.addObserver(lifecycleObserver)
    }

    abstract fun collect(
        visibilityFlow: Flow<Boolean>,
        dataFlow: Flow<T>
    )

    private inner class ComponentLifecycleObserver : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            this@BaseComponent.onStart()
        }

        override fun onResume(owner: LifecycleOwner) {
            this@BaseComponent.onResume()
        }

        override fun onPause(owner: LifecycleOwner) {
            this@BaseComponent.onPause()
        }

        override fun onStop(owner: LifecycleOwner) {
            this@BaseComponent.onStop()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            this@BaseComponent.onDestroy()
            owner.lifecycle.removeObserver(this)
        }
    }

    protected open fun onStart() {}

    protected open fun onResume() {}

    protected open fun onPause() {}

    protected open fun onStop() {}

    protected open fun onDestroy() {}
}
