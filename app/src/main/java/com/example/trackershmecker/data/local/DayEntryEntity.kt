package com.example.trackershmecker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_entries")
data class DayEntryEntity(
    @PrimaryKey
    val date: String,
    val activityType: String?,
    val note: String?,
)
