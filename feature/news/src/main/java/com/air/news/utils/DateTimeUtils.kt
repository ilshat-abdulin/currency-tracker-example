package com.air.news.utils

import android.content.Context
import com.air.news.R
import java.util.*
import java.util.concurrent.TimeUnit

fun aTimeAgo(time: Long, context: Context): String {

    val calendar = GregorianCalendar()
    calendar.timeZone = TimeZone.getDefault()
    val currentTime = calendar.timeInMillis / 1000L
    val ago = currentTime - time

    return if (ago < 60) {
        context.getString(R.string.now)
    } else if (ago in 60..3599) {
        val minutes = TimeUnit.SECONDS.toMinutes(ago)
        context.getString(R.string.minutes_ago, minutes)
    } else {
        val hours = TimeUnit.SECONDS.toHours(ago)
        context.getString(R.string.hours_ago, hours)
    }
}