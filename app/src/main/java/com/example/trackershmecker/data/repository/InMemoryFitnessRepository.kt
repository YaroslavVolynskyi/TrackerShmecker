package com.example.trackershmecker.data.repository

import com.example.trackershmecker.data.SampleData
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.DayEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class InMemoryFitnessRepository : FitnessRepository {

    private val allEntries = mutableMapOf<LocalDate, DayEntry>().apply {
        putAll(SampleData.generate2025Data())
        putAll(SampleData.generateSampleEntries())
    }

    private val _entriesFlow = MutableStateFlow(allEntries.toMap())

    override fun getEntries(): Flow<Map<LocalDate, DayEntry>> = _entriesFlow.asStateFlow()

    override suspend fun logActivity(date: LocalDate, type: ActivityType) {
        val existing = allEntries[date]
        allEntries[date] = DayEntry(date = date, activityType = type, note = existing?.note)
        _entriesFlow.value = allEntries.toMap()
    }

    override suspend fun updateNote(date: LocalDate, note: String) {
        val existing = allEntries[date]
        allEntries[date] = (existing ?: DayEntry(date = date)).copy(note = note.ifBlank { null })
        _entriesFlow.value = allEntries.toMap()
    }

    override suspend fun getYearTotals(year: Int): Map<ActivityType, Int> {
        val counts = mutableMapOf<ActivityType, Int>()
        ActivityType.entries.forEach { counts[it] = 0 }
        allEntries.values
            .filter { it.date.year == year && it.activityType != null }
            .forEach { counts[it.activityType!!] = (counts[it.activityType] ?: 0) + 1 }
        return counts
    }
}
