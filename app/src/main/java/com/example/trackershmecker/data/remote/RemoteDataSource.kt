package com.example.trackershmecker.data.remote

import com.example.trackershmecker.data.local.DayEntryEntity
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun fetchAllEntries(): List<DayEntryEntity>
    suspend fun uploadAllEntries(entries: List<DayEntryEntity>)
    suspend fun uploadEntry(entity: DayEntryEntity)
}

class FirebaseRemoteDataSource @Inject constructor(
    private val api: FirebaseApiService,
) : RemoteDataSource {

    override suspend fun fetchAllEntries(): List<DayEntryEntity> {
        val remote = api.getAllEntries() ?: return emptyList()
        return remote.map { (date, dto) ->
            DayEntryEntity(
                date = date,
                activityType = dto.activityType,
                note = dto.note,
            )
        }
    }

    override suspend fun uploadAllEntries(entries: List<DayEntryEntity>) {
        val dtoMap = entries.associate { entity ->
            entity.date to DayEntryDto(
                activityType = entity.activityType,
                note = entity.note,
            )
        }
        api.putAllEntries(dtoMap)
    }

    override suspend fun uploadEntry(entity: DayEntryEntity) {
        api.putEntry(
            date = entity.date,
            entry = DayEntryDto(
                activityType = entity.activityType,
                note = entity.note,
            ),
        )
    }
}
