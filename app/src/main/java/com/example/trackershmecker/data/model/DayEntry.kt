package com.example.trackershmecker.data.model

import java.time.LocalDate

data class DayEntry(
    val date: LocalDate,
    val activityTypes: List<ActivityType> = emptyList(),
    val note: String? = null,
)
