package com.example.trackershmecker.data

import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.ActivityType.*
import com.example.trackershmecker.data.model.DayEntry
import java.time.LocalDate

object SampleData {

    private val RAW: Map<String, Map<Int, ActivityType>> = mapOf(
        // ── 2024 ──
        "2024-4" to mapOf(
            1 to GYM, 3 to GYM, 4 to GYM, 5 to GYM, 8 to GYM, 10 to GYM,
            11 to GYM, 16 to GYM, 18 to GYM, 22 to GYM, 23 to GYM, 27 to GYM,
            6 to TENNIS, 14 to TENNIS, 17 to TENNIS, 19 to TENNIS, 20 to TENNIS,
            24 to TENNIS, 25 to TENNIS, 29 to TENNIS, 30 to TENNIS,
        ),
        "2024-5" to mapOf(
            2 to GYM, 4 to GYM, 9 to GYM, 11 to GYM, 13 to GYM, 17 to GYM,
            20 to GYM, 21 to GYM, 22 to GYM, 25 to GYM, 28 to GYM, 31 to GYM,
            3 to TENNIS, 6 to TENNIS, 8 to TENNIS, 10 to TENNIS, 15 to TENNIS,
            27 to TENNIS, 30 to TENNIS,
            5 to POOL, 7 to POOL, 12 to POOL, 29 to POOL,
        ),
        "2024-6" to mapOf(
            3 to GYM, 18 to GYM, 20 to GYM, 24 to GYM, 27 to GYM, 30 to GYM,
            1 to TENNIS, 2 to TENNIS, 17 to TENNIS, 19 to TENNIS, 22 to TENNIS,
            23 to TENNIS, 25 to TENNIS, 26 to TENNIS, 28 to TENNIS,
            4 to POOL,
        ),
        "2024-7" to mapOf(
            3 to GYM, 6 to GYM, 7 to GYM, 9 to GYM, 12 to GYM, 14 to GYM,
            15 to GYM, 21 to GYM, 22 to GYM, 27 to GYM,
            1 to TENNIS, 2 to TENNIS, 8 to TENNIS, 10 to TENNIS, 16 to TENNIS,
            23 to TENNIS, 24 to TENNIS, 26 to TENNIS, 28 to TENNIS, 29 to TENNIS,
            30 to TENNIS,
            5 to POOL,
            4 to DAY_OFF, 11 to DAY_OFF, 13 to DAY_OFF, 17 to DAY_OFF,
            18 to DAY_OFF, 19 to DAY_OFF, 20 to DAY_OFF, 25 to DAY_OFF,
            31 to DAY_OFF,
        ),
        "2024-8" to mapOf(
            4 to GYM, 6 to GYM, 7 to GYM, 10 to GYM, 11 to GYM, 13 to GYM,
            16 to GYM, 18 to GYM, 19 to GYM, 21 to GYM, 26 to GYM, 28 to GYM,
            31 to GYM,
            1 to TENNIS, 2 to TENNIS, 3 to TENNIS, 9 to TENNIS, 12 to TENNIS,
            17 to TENNIS, 24 to TENNIS, 25 to TENNIS, 30 to TENNIS,
            5 to POOL, 8 to POOL, 14 to POOL, 27 to POOL,
            15 to DAY_OFF, 20 to DAY_OFF, 22 to DAY_OFF, 23 to DAY_OFF,
            29 to DAY_OFF,
        ),
        "2024-9" to mapOf(
            3 to GYM, 5 to GYM, 7 to GYM, 9 to GYM, 10 to GYM, 12 to GYM,
            13 to GYM, 15 to GYM, 16 to GYM, 20 to GYM, 29 to GYM, 30 to GYM,
            6 to TENNIS, 8 to TENNIS, 14 to TENNIS, 18 to TENNIS,
            17 to POOL,
            1 to DAY_OFF, 2 to DAY_OFF, 4 to DAY_OFF, 11 to DAY_OFF,
            19 to DAY_OFF, 21 to DAY_OFF, 22 to DAY_OFF, 23 to DAY_OFF,
            24 to DAY_OFF, 25 to DAY_OFF, 26 to DAY_OFF, 27 to DAY_OFF,
            28 to DAY_OFF,
        ),
        "2024-10" to mapOf(
            1 to GYM, 2 to GYM, 4 to GYM, 5 to GYM, 7 to GYM, 8 to GYM,
            9 to GYM, 10 to GYM, 12 to GYM, 13 to GYM, 14 to GYM, 15 to GYM,
            17 to GYM, 18 to GYM, 19 to GYM, 21 to GYM, 22 to GYM, 24 to GYM,
            25 to GYM, 27 to GYM, 28 to GYM, 29 to GYM, 30 to GYM, 31 to GYM,
            3 to TENNIS, 6 to TENNIS, 16 to TENNIS,
            11 to DAY_OFF, 20 to DAY_OFF, 23 to DAY_OFF, 26 to DAY_OFF,
        ),
        "2024-11" to mapOf(
            2 to GYM, 3 to GYM, 4 to GYM, 5 to GYM, 6 to GYM, 8 to GYM,
            10 to GYM, 11 to GYM, 12 to GYM, 14 to GYM, 15 to GYM, 16 to GYM,
            18 to GYM, 19 to GYM, 23 to GYM, 24 to GYM, 25 to GYM, 26 to GYM,
            30 to GYM,
            28 to TENNIS,
            1 to DAY_OFF, 7 to DAY_OFF, 9 to DAY_OFF, 13 to DAY_OFF,
            17 to DAY_OFF, 20 to DAY_OFF, 21 to DAY_OFF, 22 to DAY_OFF,
            27 to DAY_OFF, 29 to DAY_OFF,
        ),
        "2024-12" to mapOf(
            3 to GYM, 4 to GYM, 14 to GYM, 15 to GYM, 16 to GYM, 17 to GYM,
            18 to GYM, 20 to GYM, 21 to GYM, 22 to GYM, 26 to GYM, 27 to GYM,
            28 to GYM, 29 to GYM, 31 to GYM,
            11 to TENNIS, 25 to TENNIS,
            1 to DAY_OFF, 2 to DAY_OFF, 5 to DAY_OFF, 6 to DAY_OFF,
            7 to DAY_OFF, 8 to DAY_OFF, 9 to DAY_OFF, 10 to DAY_OFF,
            12 to DAY_OFF, 13 to DAY_OFF, 19 to DAY_OFF, 23 to DAY_OFF,
            24 to DAY_OFF, 30 to DAY_OFF,
        ),
        // ── 2025 ──
        "2025-1" to mapOf(
            1 to GYM, 3 to GYM, 6 to GYM, 8 to GYM, 9 to GYM, 11 to GYM,
            12 to GYM, 13 to GYM, 14 to GYM, 17 to GYM, 20 to GYM, 21 to GYM,
            23 to GYM, 24 to GYM, 25 to GYM, 28 to GYM, 30 to GYM, 31 to GYM,
            2 to TENNIS, 7 to TENNIS,
            4 to DAY_OFF, 5 to DAY_OFF, 10 to DAY_OFF, 15 to DAY_OFF,
            16 to DAY_OFF, 18 to DAY_OFF, 19 to DAY_OFF, 22 to DAY_OFF,
            26 to DAY_OFF, 27 to DAY_OFF, 29 to DAY_OFF,
        ),
        "2025-2" to mapOf(
            2 to GYM, 3 to GYM, 5 to GYM, 7 to GYM, 9 to GYM, 11 to GYM,
            13 to GYM, 14 to GYM, 16 to GYM, 19 to GYM, 20 to GYM, 24 to GYM,
            27 to GYM,
            8 to TENNIS, 15 to TENNIS,
            1 to DAY_OFF, 4 to DAY_OFF, 6 to DAY_OFF, 10 to DAY_OFF,
            12 to DAY_OFF, 17 to DAY_OFF, 18 to DAY_OFF, 21 to DAY_OFF,
            22 to DAY_OFF, 23 to DAY_OFF, 25 to DAY_OFF, 26 to DAY_OFF,
            28 to DAY_OFF,
        ),
        "2025-3" to mapOf(
            1 to GYM, 3 to GYM, 13 to GYM, 19 to GYM, 20 to GYM, 24 to GYM,
            25 to GYM, 27 to GYM, 28 to GYM, 31 to GYM,
            22 to TENNIS, 23 to TENNIS, 30 to TENNIS,
            2 to DAY_OFF, 4 to DAY_OFF, 5 to DAY_OFF, 6 to DAY_OFF,
            7 to DAY_OFF, 8 to DAY_OFF, 9 to DAY_OFF, 10 to DAY_OFF,
            11 to DAY_OFF, 12 to DAY_OFF, 14 to DAY_OFF, 15 to DAY_OFF,
            16 to DAY_OFF, 17 to DAY_OFF, 18 to DAY_OFF, 21 to DAY_OFF,
            26 to DAY_OFF, 29 to DAY_OFF,
        ),
        "2025-4" to mapOf(
            2 to GYM, 3 to GYM, 4 to GYM, 7 to GYM, 8 to GYM, 9 to GYM,
            11 to GYM, 13 to GYM, 14 to GYM, 16 to GYM, 17 to GYM, 18 to GYM,
            21 to GYM, 22 to GYM, 23 to GYM, 25 to GYM, 27 to GYM, 28 to GYM,
            29 to GYM, 30 to GYM,
            6 to TENNIS,
            1 to DAY_OFF, 5 to DAY_OFF, 10 to DAY_OFF, 12 to DAY_OFF,
            15 to DAY_OFF, 19 to DAY_OFF, 20 to DAY_OFF, 24 to DAY_OFF,
            26 to DAY_OFF,
        ),
        "2025-5" to mapOf(
            1 to GYM, 5 to GYM, 7 to GYM, 8 to GYM, 10 to GYM, 13 to GYM,
            14 to GYM, 15 to GYM, 17 to GYM, 19 to GYM, 20 to GYM, 24 to GYM,
            25 to GYM, 27 to GYM, 28 to GYM, 29 to GYM,
            11 to TENNIS,
            2 to DAY_OFF, 3 to DAY_OFF, 4 to DAY_OFF, 6 to DAY_OFF,
            9 to DAY_OFF, 12 to DAY_OFF, 16 to DAY_OFF, 18 to DAY_OFF,
            21 to DAY_OFF, 22 to DAY_OFF, 23 to DAY_OFF, 26 to DAY_OFF,
            30 to DAY_OFF, 31 to DAY_OFF,
        ),
        "2025-6" to mapOf(
            1 to GYM, 2 to GYM, 7 to GYM, 8 to GYM, 10 to GYM, 12 to GYM,
            14 to GYM, 17 to GYM, 19 to GYM, 20 to GYM, 22 to GYM, 24 to GYM,
            26 to GYM, 28 to GYM,
            9 to TENNIS, 11 to TENNIS, 13 to TENNIS, 16 to TENNIS,
            21 to TENNIS, 25 to TENNIS, 29 to TENNIS,
            3 to DAY_OFF, 4 to DAY_OFF, 5 to DAY_OFF, 6 to DAY_OFF,
            15 to DAY_OFF, 18 to DAY_OFF, 23 to DAY_OFF, 27 to DAY_OFF,
            30 to DAY_OFF,
        ),
        "2025-7" to mapOf(
            1 to GYM, 2 to GYM, 4 to GYM, 6 to GYM, 7 to GYM, 10 to GYM,
            11 to GYM, 13 to GYM, 14 to GYM, 16 to GYM, 18 to GYM, 20 to GYM,
            21 to GYM, 24 to GYM, 26 to GYM, 28 to GYM, 30 to GYM, 31 to GYM,
            12 to TENNIS, 15 to TENNIS, 17 to TENNIS, 27 to TENNIS,
            3 to DAY_OFF, 5 to DAY_OFF, 8 to DAY_OFF, 9 to DAY_OFF,
            19 to DAY_OFF, 22 to DAY_OFF, 23 to DAY_OFF, 25 to DAY_OFF,
            29 to DAY_OFF,
        ),
        "2025-8" to mapOf(
            3 to GYM, 5 to GYM, 6 to GYM, 7 to GYM, 10 to GYM, 12 to GYM,
            13 to GYM, 15 to GYM, 17 to GYM, 18 to GYM, 20 to GYM, 21 to GYM,
            23 to GYM, 24 to GYM, 26 to GYM, 28 to GYM, 30 to GYM,
            2 to TENNIS, 14 to TENNIS, 19 to TENNIS, 25 to TENNIS,
            1 to DAY_OFF, 4 to DAY_OFF, 8 to DAY_OFF, 9 to DAY_OFF,
            11 to DAY_OFF, 16 to DAY_OFF, 22 to DAY_OFF, 27 to DAY_OFF,
            29 to DAY_OFF, 31 to DAY_OFF,
        ),
        "2025-9" to mapOf(
            2 to GYM, 3 to GYM, 6 to GYM, 7 to GYM, 9 to GYM, 13 to GYM,
            14 to GYM, 17 to GYM, 19 to GYM, 22 to GYM, 23 to GYM, 25 to GYM,
            27 to GYM, 29 to GYM, 30 to GYM,
            4 to TENNIS, 10 to TENNIS, 26 to TENNIS,
            1 to DAY_OFF, 5 to DAY_OFF, 8 to DAY_OFF, 11 to DAY_OFF,
            12 to DAY_OFF, 15 to DAY_OFF, 16 to DAY_OFF, 18 to DAY_OFF,
            20 to DAY_OFF, 21 to DAY_OFF, 24 to DAY_OFF, 28 to DAY_OFF,
        ),
        "2025-10" to mapOf(
            1 to GYM, 4 to GYM, 5 to GYM, 17 to GYM, 18 to GYM, 19 to GYM,
            22 to GYM, 23 to GYM, 24 to GYM, 26 to GYM, 27 to GYM, 28 to GYM,
            20 to TENNIS, 29 to TENNIS,
            2 to DAY_OFF, 3 to DAY_OFF, 6 to DAY_OFF, 7 to DAY_OFF,
            8 to DAY_OFF, 9 to DAY_OFF, 10 to DAY_OFF, 11 to DAY_OFF,
            12 to DAY_OFF, 13 to DAY_OFF, 14 to DAY_OFF, 15 to DAY_OFF,
            16 to DAY_OFF, 21 to DAY_OFF, 25 to DAY_OFF, 30 to DAY_OFF,
            31 to DAY_OFF,
        ),
        "2025-11" to mapOf(
            1 to GYM, 2 to GYM, 4 to GYM, 6 to GYM, 7 to GYM, 10 to GYM,
            12 to GYM, 13 to GYM, 14 to GYM, 16 to GYM, 17 to GYM, 18 to GYM,
            19 to GYM, 21 to GYM, 24 to GYM, 26 to GYM, 29 to GYM,
            8 to TENNIS, 11 to TENNIS, 15 to TENNIS, 22 to TENNIS,
            27 to TENNIS, 30 to TENNIS,
            3 to DAY_OFF, 5 to DAY_OFF, 9 to DAY_OFF, 20 to DAY_OFF,
            23 to DAY_OFF, 25 to DAY_OFF, 28 to DAY_OFF,
        ),
        "2025-12" to mapOf(
            1 to GYM, 2 to GYM, 4 to GYM, 7 to GYM, 9 to GYM, 10 to GYM,
            13 to GYM, 14 to GYM, 16 to GYM, 18 to GYM, 19 to GYM, 21 to GYM,
            23 to GYM, 26 to GYM, 27 to GYM, 28 to GYM, 30 to GYM, 31 to GYM,
            3 to DAY_OFF, 5 to DAY_OFF, 6 to DAY_OFF, 8 to DAY_OFF,
            11 to DAY_OFF, 12 to DAY_OFF, 15 to DAY_OFF, 17 to DAY_OFF,
            20 to DAY_OFF, 22 to DAY_OFF, 24 to DAY_OFF, 25 to DAY_OFF,
            29 to DAY_OFF,
        ),
        // ── 2026 ──
        "2026-1" to mapOf(
            3 to GYM, 5 to GYM, 6 to GYM, 8 to GYM, 10 to GYM, 13 to GYM,
            14 to GYM, 16 to GYM, 18 to GYM, 20 to GYM, 21 to GYM, 22 to GYM,
            23 to GYM, 26 to GYM, 30 to GYM,
            4 to TENNIS, 11 to TENNIS, 17 to TENNIS, 19 to TENNIS, 25 to TENNIS,
            31 to TENNIS,
            1 to DAY_OFF, 2 to DAY_OFF, 7 to DAY_OFF,
            9 to DAY_OFF, 12 to DAY_OFF, 15 to DAY_OFF, 24 to DAY_OFF,
            27 to DAY_OFF, 28 to DAY_OFF, 29 to DAY_OFF,
        ),
        "2026-2" to mapOf(
            2 to GYM, 3 to GYM, 4 to GYM, 5 to GYM, 6 to GYM, 10 to GYM,
            11 to GYM, 14 to GYM, 15 to GYM, 18 to GYM, 20 to GYM, 21 to GYM,
            25 to GYM, 28 to GYM,
            7 to TENNIS, 16 to TENNIS, 22 to TENNIS,
            1 to DAY_OFF, 8 to DAY_OFF, 9 to DAY_OFF,
            12 to DAY_OFF, 13 to DAY_OFF, 17 to DAY_OFF, 19 to DAY_OFF,
            23 to DAY_OFF, 24 to DAY_OFF, 26 to DAY_OFF, 27 to DAY_OFF,
        ),
        "2026-3" to mapOf(
            3 to GYM, 5 to GYM, 9 to GYM, 11 to GYM, 12 to GYM, 13 to GYM,
            15 to GYM, 16 to GYM, 20 to GYM, 22 to GYM, 24 to GYM, 25 to GYM,
            1 to TENNIS,
            2 to DAY_OFF, 4 to DAY_OFF, 6 to DAY_OFF, 7 to DAY_OFF,
            8 to DAY_OFF, 10 to DAY_OFF, 14 to DAY_OFF, 17 to DAY_OFF,
            18 to DAY_OFF, 19 to DAY_OFF, 21 to DAY_OFF, 23 to DAY_OFF,
            26 to DAY_OFF, 27 to DAY_OFF, 28 to DAY_OFF, 29 to DAY_OFF,
            30 to DAY_OFF, 31 to DAY_OFF,
        ),
        "2026-4" to mapOf(
            4 to GYM, 6 to GYM, 7 to GYM, 9 to GYM, 12 to GYM, 13 to GYM,
            14 to GYM, 18 to GYM,
            1 to DAY_OFF, 2 to DAY_OFF, 3 to DAY_OFF, 5 to DAY_OFF,
            8 to DAY_OFF, 10 to DAY_OFF, 11 to DAY_OFF, 15 to DAY_OFF,
            16 to DAY_OFF, 17 to DAY_OFF,
        ),
    )

    private val NOTES: Map<LocalDate, String> = mapOf(
        LocalDate.of(2025, 6, 8) to "78kg, 11% body fat",
        LocalDate.of(2025, 6, 22) to "78.5kg, 9.4% fat",
        LocalDate.of(2025, 8, 21) to "81.2kg, 7% body fat",
        LocalDate.of(2026, 1, 5) to "81.3 kg в кроссах",
        LocalDate.of(2026, 2, 20) to "82.7 kg, 8.6% fat",
        LocalDate.of(2026, 4, 4) to "78.5kg, 6.5% fat",
    )

    fun generateEntries(): Map<LocalDate, DayEntry> {
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
}
