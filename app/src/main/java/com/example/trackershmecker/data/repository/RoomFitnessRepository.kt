package com.example.trackershmecker.data.repository

import com.example.trackershmecker.data.local.DayEntryDao
import com.example.trackershmecker.data.local.DayEntryEntity
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.DayEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class RoomFitnessRepository @Inject constructor(
    private val dao: DayEntryDao,
) : FitnessRepository {

    override fun getEntries(): Flow<Map<LocalDate, DayEntry>> {
        return dao.observeAll().map { entities ->
            entities.associate { entity ->
                val date = LocalDate.parse(entity.date)
                date to DayEntry(
                    date = date,
                    activityType = entity.activityType?.let { ActivityType.valueOf(it) },
                    note = entity.note,
                )
            }
        }
    }

    override suspend fun logActivity(date: LocalDate, type: ActivityType) {
        val existing = dao.getByDate(date.toString())
        dao.upsert(
            DayEntryEntity(
                date = date.toString(),
                activityType = type.name,
                note = existing?.note,
            )
        )
    }

    override suspend fun updateNote(date: LocalDate, note: String) {
        val existing = dao.getByDate(date.toString())
        val cleanNote = note.ifBlank { null }
        dao.upsert(
            DayEntryEntity(
                date = date.toString(),
                activityType = existing?.activityType,
                note = cleanNote,
            )
        )
    }

    override suspend fun getYearTotals(year: Int): Map<ActivityType, Int> {
        val entities = dao.getByYear(year.toString())
        val counts = ActivityType.entries.associateWith { 0 }.toMutableMap()
        entities.forEach { entity ->
            entity.activityType?.let { typeName ->
                val type = ActivityType.valueOf(typeName)
                counts[type] = (counts[type] ?: 0) + 1
            }
        }
        return counts
    }
}
