package com.example.trackershmecker.data.repository

import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.DayEntry
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface FitnessRepository {
    fun getEntries(): Flow<Map<LocalDate, DayEntry>>
    suspend fun logActivity(date: LocalDate, type: ActivityType)
    suspend fun addActivity(date: LocalDate, type: ActivityType)
    suspend fun updateNote(date: LocalDate, note: String)
    suspend fun getYearTotals(year: Int): Map<ActivityType, Int>
}
