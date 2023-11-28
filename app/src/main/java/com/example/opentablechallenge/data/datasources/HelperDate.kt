package com.example.opentablechallenge.data.datasources

import java.time.LocalDateTime

object HelperDate {

    private val workingHours = 17 .. 23
    private val minutesRange = 0..45
    private const val MINUTES_STEP = 15

    private val MAX_DATE = LocalDateTime.now()
        .withHour(23)
        .withMinute(0)
        .withSecond(0)
        .withNano(0)

    val MIN_DATE = LocalDateTime.now()
        .withHour(17)
        .withMinute(0)
        .withSecond(0)
        .withNano(0)

    fun createDateRange(): List<LocalDateTime> {
        val datesList = mutableListOf<LocalDateTime>()
        for (hour in workingHours) {
            val now = LocalDateTime.now()
                .withHour(hour)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
            for (minutes in minutesRange step MINUTES_STEP) {
                val nowPlusTime = now.plusMinutes(minutes.toLong())
                if (nowPlusTime <= MAX_DATE) {
                    datesList.add(nowPlusTime)
                }
            }
        }
        return datesList
    }

}
