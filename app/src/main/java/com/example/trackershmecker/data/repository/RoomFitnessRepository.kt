package com.example.trackershmecker.data.repository

import com.example.trackershmecker.data.local.DayEntryDao
import com.example.trackershmecker.data.local.DayEntryEntity
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.DayEntry
import com.example.trackershmecker.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class RoomFitnessRepository @Inject constructor(
    private val dao: DayEntryDao,
    private val remoteDataSource: RemoteDataSource,
) : FitnessRepository {

    override fun getEntries(): Flow<Map<LocalDate, DayEntry>> {
        return dao.observeAll().map { entities ->
            entities.associate { entity ->
                val date = LocalDate.parse(entity.date)
                date to DayEntry(
                    date = date,
                    activityTypes = parseTypes(entity.activityType),
                    note = entity.note,
                )
            }
        }
    }

    override suspend fun logActivity(date: LocalDate, type: ActivityType) {
        val existing = dao.getByDate(date.toString())
        val entry = DayEntryEntity(
            date = date.toString(),
            activityType = type.name,
            note = existing?.note,
        )
        dao.upsert(entry)
        pushEntryToRemote(entry)
    }

    override suspend fun addActivity(date: LocalDate, type: ActivityType) {
        val existing = dao.getByDate(date.toString())
        val currentTypes = existing?.activityType
            ?.split(",")
            ?.toMutableList()
            ?: mutableListOf()
        if (currentTypes.contains(type.name)) {
            currentTypes.remove(type.name)
        } else {
            currentTypes.add(type.name)
        }
        val entry = DayEntryEntity(
            date = date.toString(),
            activityType = currentTypes.joinToString(",").ifEmpty { null },
            note = existing?.note,
        )
        dao.upsert(entry)
        pushEntryToRemote(entry)
    }

    override suspend fun updateNote(date: LocalDate, note: String) {
        val existing = dao.getByDate(date.toString())
        val cleanNote = note.ifBlank { null }
        val entry = DayEntryEntity(
            date = date.toString(),
            activityType = existing?.activityType,
            note = cleanNote,
        )
        dao.upsert(entry)
        pushEntryToRemote(entry)
    }

    private suspend fun pushEntryToRemote(entry: DayEntryEntity) {
        try {
            remoteDataSource.uploadEntry(entry)
        } catch (e: Exception) {
            android.util.Log.e("RoomFitnessRepo", "Failed to push entry to remote", e)
        }
    }

    override suspend fun getYearTotals(year: Int): Map<ActivityType, Int> {
        val entities = dao.getByYear(year.toString())
        val counts = ActivityType.entries.associateWith { 0 }.toMutableMap()
        entities.forEach { entity ->
            parseTypes(entity.activityType).forEach { type ->
                counts[type] = (counts[type] ?: 0) + 1
            }
        }
        return counts
    }

    private fun parseTypes(value: String?): List<ActivityType> {
        if (value.isNullOrBlank()) return emptyList()
        return value.split(",").map { ActivityType.valueOf(it) }
    }
}
