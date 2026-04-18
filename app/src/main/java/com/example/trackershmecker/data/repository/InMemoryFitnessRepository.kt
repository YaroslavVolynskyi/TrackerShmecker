package com.example.trackershmecker.data.repository

import com.example.trackershmecker.data.SampleData
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.DayEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class InMemoryFitnessRepository : FitnessRepository {

    private val allEntries = mutableMapOf<LocalDate, DayEntry>().apply {
        putAll(SampleData.generateEntries())
    }

    private val _entriesFlow = MutableStateFlow(allEntries.toMap())

    override fun getEntries(): Flow<Map<LocalDate, DayEntry>> = _entriesFlow.asStateFlow()

    override suspend fun logActivity(date: LocalDate, type: ActivityType) {
        val existing = allEntries[date]
        allEntries[date] = DayEntry(date = date, activityTypes = listOf(type), note = existing?.note)
        _entriesFlow.value = allEntries.toMap()
    }

    override suspend fun addActivity(date: LocalDate, type: ActivityType) {
        val existing = allEntries[date]
        val currentTypes = existing?.activityTypes?.toMutableList() ?: mutableListOf()
        if (currentTypes.contains(type)) {
            currentTypes.remove(type)
        } else {
            currentTypes.add(type)
        }
        allEntries[date] = DayEntry(date = date, activityTypes = currentTypes, note = existing?.note)
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
            .filter { it.date.year == year }
            .forEach { entry ->
                entry.activityTypes.forEach { type ->
                    counts[type] = (counts[type] ?: 0) + 1
                }
            }
        return counts
    }
}
