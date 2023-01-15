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

    val local = Locale.getDefault().language
    val res: String

    if (local == RU_LOCAL) {
        res = if (ago < 60) {
            context.getString(R.string.now)
        } else if (ago in 60..3599) {
            val minutes = TimeUnit.SECONDS.toMinutes(ago)
            getMinutesByRuLocal(minutes.toInt(), context)
        } else {
            val hours = TimeUnit.SECONDS.toHours(ago)
            getHoursByRuLocal(hours.toInt(), context)
        }
    } else {
        res = if (ago < 60) {
            context.getString(R.string.now)
        } else if (ago in 60..3599) {
            val minutes = TimeUnit.SECONDS.toMinutes(ago)
            context.getString(R.string.minutes_ago_1, minutes)
        } else {
            val hours = TimeUnit.SECONDS.toHours(ago)
            context.getString(R.string.hours_ago_1, hours)
        }
    }

    return res
}

private fun getMinutesByRuLocal(minutes: Int, context: Context) = when (minutes) {
    1, in 21..51 step 10 -> context.getString(R.string.minutes_ago_1, minutes)
    in 5..20, in 25..30, in 35..40, in 45..50,
    in 55..59 -> context.getString(R.string.minutes_ago_2, minutes)
    else -> {
        context.getString(R.string.minutes_ago_3, minutes)
    }
}

private fun getHoursByRuLocal(hours: Int, context: Context) = when(hours) {
    1, 21 -> context.getString(R.string.hours_ago_1, hours)
    in 2..4, in 22..24 -> context.getString(R.string.hours_ago_2, hours)
    else -> {
        context.getString(R.string.hours_ago_3, hours)
    }
}

private const val RU_LOCAL = "ru"