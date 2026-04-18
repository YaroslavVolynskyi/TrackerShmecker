package com.example.trackershmecker.data

import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.ActivityType.*
import com.example.trackershmecker.data.model.DayEntry
import java.time.LocalDate

object SampleData {

    private val RAW: Map<String, Map<Int, ActivityType>> = mapOf(
        "2025-11" to mapOf(
            3 to GYM, 5 to GYM, 7 to TENNIS, 10 to GYM, 12 to GYM,
            14 to POOL, 17 to GYM, 19 to GYM, 21 to DAY_OFF, 24 to GYM, 26 to GYM, 28 to GYM,
        ),
        "2025-12" to mapOf(
            1 to GYM, 3 to GYM, 5 to TENNIS, 8 to GYM, 10 to GYM,
            12 to DAY_OFF, 15 to GYM, 17 to GYM, 19 to GYM, 22 to GYM,
            24 to DAY_OFF, 26 to GYM, 29 to GYM, 31 to GYM,
        ),
        "2026-1" to mapOf(
            1 to GYM, 2 to TENNIS, 3 to TENNIS, 4 to POOL, 5 to GYM, 6 to DAY_OFF,
            7 to GYM, 8 to GYM, 9 to TENNIS, 10 to GYM, 11 to DAY_OFF, 12 to GYM,
            13 to GYM, 14 to TENNIS, 15 to GYM, 16 to DAY_OFF, 17 to GYM, 18 to DAY_OFF,
            19 to GYM, 20 to GYM, 21 to TENNIS, 22 to GYM, 23 to GYM, 24 to DAY_OFF,
            25 to GYM, 26 to TENNIS, 27 to GYM, 28 to GYM, 29 to GYM, 30 to GYM, 31 to DAY_OFF,
        ),
        "2026-2" to mapOf(
            1 to GYM, 2 to GYM, 3 to TENNIS, 4 to POOL, 5 to GYM, 6 to GYM, 7 to DAY_OFF,
            9 to GYM, 10 to GYM, 11 to GYM, 12 to GYM, 13 to GYM, 14 to GYM, 15 to DAY_OFF,
            17 to GYM, 20 to GYM, 26 to GYM,
        ),
        "2026-3" to mapOf(
            1 to GYM, 2 to GYM, 3 to TENNIS, 6 to GYM, 7 to GYM, 8 to GYM,
            10 to GYM, 13 to GYM, 14 to GYM, 17 to GYM, 18 to GYM,
            20 to GYM, 21 to GYM, 24 to GYM, 25 to GYM, 26 to GYM,
            29 to GYM, 30 to GYM, 31 to GYM,
        ),
        "2026-4" to mapOf(
            2 to GYM, 4 to GYM, 7 to GYM, 9 to GYM, 10 to GYM,
            11 to GYM, 13 to GYM, 14 to GYM, 15 to GYM,
        ),
    )

    private val NOTES: Map<LocalDate, String> = mapOf(
        LocalDate.of(2026, 4, 7) to "80 kg",
        LocalDate.of(2026, 4, 2) to "80.4 kg",
        LocalDate.of(2026, 4, 14) to "79.6 kg, deadlift PR",
    )

    fun generateSampleEntries(): Map<LocalDate, DayEntry> {
        val entries = mutableMapOf<LocalDate, DayEntry>()
        for ((key, days) in RAW) {
            val (y, m) = key.split("-").map { it.toInt() }
            for ((d, type) in days) {
                val date = LocalDate.of(y, m, d)
                entries[date] = DayEntry(
                    date = date,
                    activityType = type,
                    note = NOTES[date],
                )
            }
        }
        return entries
    }

    /** Generate simulated 2025 data for year comparison. */
    fun generate2025Data(): Map<LocalDate, DayEntry> {
        val entries = mutableMapOf<LocalDate, DayEntry>()
        for (m in 1..12) {
            val daysInMonth = java.time.YearMonth.of(2025, m).lengthOfMonth()
            for (d in 1..daysInMonth) {
                val r = kotlin.math.sin(m * 7.0 + d * 2.3) * 0.5 + 0.5
                val type = when {
                    r > 0.78 -> GYM
                    r > 0.72 -> TENNIS
                    r > 0.68 -> POOL
                    r > 0.64 -> DAY_OFF
                    else -> null
                }
                if (type != null) {
                    val date = LocalDate.of(2025, m, d)
                    entries[date] = DayEntry(date = date, activityType = type)
                }
            }
        }
        return entries
    }
}
