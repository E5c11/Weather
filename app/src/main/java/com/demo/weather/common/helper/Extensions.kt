package com.demo.weather.common.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.demo.weather.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

fun <T> Flow<T>.collectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend (value: T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        collect { action(it) }
    }
}

fun Context.hasLocationPermission() =
    (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED)

fun Long.getDayOfWeek(): String = SimpleDateFormat("EEE", Locale.ENGLISH).format(this * 1000)

fun String.toCamelCase(delimiter: String = " "): String =
    split(delimiter).joinToString(delimiter) { word ->
        word.replaceFirstChar(Char::titlecaseChar)
    }

fun Int.weatherIcon(): Int = when (this) {
    in 1..2 -> R.drawable.sun
    in 3..6 -> R.drawable.cloud
    in 7..13, in 17..22 -> R.drawable.rain
    in 14..16 -> R.drawable.snow
    in 23..27 -> R.drawable.thunderstorm
    else -> R.drawable.cloud
}

fun Int.suggestionPath(): String = when (this) {
    in 1..2 -> "beach_access/v12/24px.svg"
    in 3..6 -> "live_tv/v11/24px.svg"
    in 7..13, in 17..22 -> "night_shelter/v6/24px.svg"
    in 14..16 -> "downhill_skiing/v4/24px.svg"
    in 23..27 -> "headset/v12/24px.svg"
    else -> "live_tv/v11/24px.svg"
}

fun getDate(date: Int = 0): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, date)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

fun String.toHour(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return try {
        val date = inputFormat.parse(this)
        if (date == null) ""
        else {
            val roundedDate = Date(((date.time + 59  *60*  1000) / (60  *60*  1000) + 1)  *(60*  60 * 1000))
            outputFormat.format(roundedDate)
        }
    } catch (e: Exception) {
        ""
    }
}

fun String.toDay(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        val date = inputFormat.parse(this)
        if (date == null) ""
        else {
            outputFormat.format(date)
        }
    } catch (e: Exception) {
        ""
    }
}