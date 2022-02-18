package com.android.yeophonebook.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtils {
    private const val YYYY_MM_DD_T_HH_MM = "yyyy-MM-dd HH:mm:ss"

    fun Long.formatMillisToDateString(): String {
        val formatter = SimpleDateFormat(YYYY_MM_DD_T_HH_MM, Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        return formatter.format(this)
    }
}
