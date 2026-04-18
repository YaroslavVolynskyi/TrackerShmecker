package com.example.trackershmecker.data.repository

import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.DayEntry
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

interface FitnessRepository {
    fun getEntries(): Flow<Map<LocalDate, DayEntry>>
    fun logActivity(date: LocalDate, type: ActivityType)
    fun updateNote(date: LocalDate, note: String)
    fun getYearTotals(year: Int): Map<ActivityType, Int>
}
