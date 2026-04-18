package com.example.trackershmecker.di

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.trackershmecker.data.SampleData

class SeedDatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val allEntries = SampleData.generateEntries()
        allEntries.values.forEach { entry ->
            db.execSQL(
                "INSERT OR REPLACE INTO day_entries (date, activityType, note) VALUES (?, ?, ?)",
                arrayOf(entry.date.toString(), entry.activityType?.name, entry.note),
            )
        }
    }
}
