package com.demo.weather.common.io

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class ActionableException(
    open var msg: String = "",
    open var error: Throwable? = null,
    open var action: String? = null
) : Exception(msg, error), Parcelable
