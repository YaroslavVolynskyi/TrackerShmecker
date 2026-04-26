package com.example.trackershmecker.ui.main

import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.DayEntry
import java.time.LocalDate
import java.time.YearMonth

enum class BottomBarMode {
    DEFAULT,
    DATE_OPTIONS,
    LOG_ACTIVITY,
}

data class MainUiState(
    val months: List<YearMonth> = emptyList(),
    val entries: Map<LocalDate, DayEntry> = emptyMap(),
    val activeMonth: YearMonth = YearMonth.now(),
    val selectedDate: LocalDate? = null,
    val today: LocalDate = LocalDate.now(),
    val streakCount: Int = 0,
    val last10Active: Int = 0,
    val yearTotals: Map<ActivityType, Int> = emptyMap(),
    val monthCounts: Map<ActivityType, Int> = emptyMap(),
    val bottomBarMode: BottomBarMode = BottomBarMode.DEFAULT,
    val showNoteCard: Boolean = false,
)
