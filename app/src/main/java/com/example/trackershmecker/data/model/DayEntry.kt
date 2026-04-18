package com.example.trackershmecker.data.model

import java.time.LocalDate

data class DayEntry(
    val date: LocalDate,
    val activityType: ActivityType? = null,
    val note: String? = null,
)
