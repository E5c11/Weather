package com.demo.weather.common.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.demo.weather.BuildConfig
import com.demo.weather.R
import com.demo.weather.common.data.ErrorState
import com.demo.weather.common.io.ActionableException
import com.demo.weather.location.io.LocationNotFoundException
import com.demo.weather.weather.io.LocationPermissionDeniedException
import com.demo.weather.weather.io.WeatherFetchException
import javax.inject.Inject

class ErrorParser @Inject constructor(private val context: Context) {

    fun parse(error: ActionableException?): ErrorState {
        return if (error == null) {
            ErrorState(msg = context.getString(R.string.general_error), title = context.getString(R.string.general_error_title))
        } else {
            when (error) {
                is LocationNotFoundException -> ErrorState(
                    msg = context.getString(R.string.location_error),
                    title = context.getString(R.string.location_error_title)
                )
                is WeatherFetchException -> ErrorState(
                    msg = context.getString(R.string.weather_error),
                    title = context.getString(R.string.weather_error_title)
                )
                is LocationPermissionDeniedException -> ErrorState(
                    msg = context.getString(R.string.location_permission_error),
                    title = context.getString(R.string.location_permission_error_title),
                    posTitle = context.getString(R.string.open_setting),
                    intent = Intent().also {
                        it.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        it.data = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                    }
                )
                else -> ErrorState(
                    msg = context.getString(R.string.general_error),
                    title = context.getString(R.string.general_error_title)
                )
            }
        }
    }
}
