package com.ele.utils

import android.annotation.SuppressLint
import android.content.Context
import com.ele.R
import java.text.SimpleDateFormat

object DateAndTimeUtil {

    internal val TAG = "DateAndTimeTag"

    private val SECOND_MILLIS = 1000
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private val DAY_MILLIS = 24 * HOUR_MILLIS


    @SuppressLint("SimpleDateFormat")
    fun getDateInMilliSeconds(dateInString: String): Long {


        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val date = sdf.parse(dateInString)

            return date.time


        } catch (e: Exception) {
        }


        return 0
    }

    fun getTimeAgo(cont:Context,time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }


        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            cont.getString(R.string.Just_now)
        } else if (diff < 2 * MINUTE_MILLIS) {
            cont.getString(R.string.a_minute_ago)
        } else if (diff < 50 * MINUTE_MILLIS) {
            (diff / MINUTE_MILLIS).toString() + " minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            cont.getString(R.string.an_hour_ago)
        } else if (diff < 24 * HOUR_MILLIS) {
            (diff / HOUR_MILLIS).toString() + " hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            cont.getString(R.string.yesterday)
        } else {
            (diff / DAY_MILLIS).toString() +" "+ cont.getString(R.string.days_ago)
        }
    }


}