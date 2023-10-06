package com.air.core_ui.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics

private val scale: Float by lazy {
    Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT
}

fun Int.asDpToPx() = (this * scale).toInt()

fun Float.asPxToDp() = this / scale

inline val Context.displayWidth: Int
    get() = resources.displayMetrics.widthPixels
