package com.oxymium.realestatemanager.utils

import java.util.*

class DateUtils {

    fun getTodayInMillis(): Long {
        val calendar: Calendar = Calendar.getInstance()
        // Set the day to 0h0m0s0ms to get only the day, as we're not interested in time
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.timeInMillis
    }
}