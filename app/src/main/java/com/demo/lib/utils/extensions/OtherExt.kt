package com.demo.lib.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.math.RoundingMode
import java.text.DecimalFormat


inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}


fun ViewGroup.inflate(@LayoutRes resId: Int): View =
    LayoutInflater.from(this.context).inflate(resId, this, false)


fun Double.truncateDecimal(): String {

    val df = DecimalFormat("#")
    df.roundingMode = RoundingMode.HALF_UP
    return df.format(this)
}





