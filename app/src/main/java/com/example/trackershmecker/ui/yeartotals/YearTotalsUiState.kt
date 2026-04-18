package com.example.trackershmecker.ui.yeartotals

import com.example.trackershmecker.data.model.ActivityType
import java.time.YearMonth

data class YearTotalsUiState(
    val year: Int = 2026,
    val totalWorkouts: Int = 0,
    val previousYearWorkouts: Int = 0,
    val activityCounts: Map<ActivityType, Int> = emptyMap(),
    val monthSummaries: List<MonthSummary> = emptyList(),
    val activeDaysPercent: Int = 0,
)

data class MonthSummary(
    val yearMonth: YearMonth,
    val workoutCount: Int,
    val dayActivities: Map<Int, List<ActivityType>>,
)
