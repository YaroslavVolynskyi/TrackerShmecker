package com.example.trackershmecker.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DayEntryDao {
    @Query("SELECT * FROM day_entries")
    fun observeAll(): Flow<List<DayEntryEntity>>

    @Query("SELECT * FROM day_entries WHERE date = :date")
    suspend fun getByDate(date: String): DayEntryEntity?

    @Query("SELECT * FROM day_entries WHERE date LIKE :yearPrefix || '%'")
    suspend fun getByYear(yearPrefix: String): List<DayEntryEntity>

    @Upsert
    suspend fun upsert(entry: DayEntryEntity)

    @Upsert
    suspend fun upsertAll(entries: List<DayEntryEntity>)
}
