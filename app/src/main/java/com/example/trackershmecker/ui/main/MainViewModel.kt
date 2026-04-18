package com.example.trackershmecker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.DayEntry
import com.example.trackershmecker.data.repository.FitnessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FitnessRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        val today = LocalDate.now()
        val months = buildMonthList(YearMonth.of(2024, 1), YearMonth.from(today))
        _uiState.update { it.copy(months = months, today = today, activeMonth = YearMonth.from(today)) }

        viewModelScope.launch {
            repository.getEntries().collect { entries ->
                _uiState.update { state ->
                    state.copy(
                        entries = entries,
                        yearTotals = computeYearTotals(entries, state.activeMonth.year),
                        monthCounts = computeMonthCounts(entries, state.activeMonth),
                        streakCount = computeStreak(entries, state.today),
                        last10Active = computeLast10(entries, state.today),
                    )
                }
            }
        }
    }

    fun onActiveMonthChanged(month: YearMonth) {
        _uiState.update { state ->
            val entries = state.entries
            state.copy(
                activeMonth = month,
                yearTotals = computeYearTotals(entries, month.year),
                monthCounts = computeMonthCounts(entries, month),
                streakCount = if (month == YearMonth.from(state.today)) computeStreak(entries, state.today) else 0,
                last10Active = if (month == YearMonth.from(state.today)) computeLast10(entries, state.today) else 0,
            )
        }
    }

    fun onDaySelected(date: LocalDate?) {
        _uiState.update { it.copy(selectedDate = if (it.selectedDate == date) null else date) }
    }

    fun onActivityLogged(type: ActivityType) {
        viewModelScope.launch {
            repository.logActivity(_uiState.value.today, type)
        }
    }

    fun onNoteUpdated(date: LocalDate, note: String) {
        viewModelScope.launch {
            repository.updateNote(date, note)
        }
    }

    fun ensureMonthVisible(target: YearMonth) {
        _uiState.update { state ->
            val months = state.months
            if (months.contains(target)) return@update state
            val newMonths = buildMonthList(
                minOf(months.first(), target),
                maxOf(months.last(), target),
            )
            state.copy(months = newMonths)
        }
    }

    private fun buildMonthList(start: YearMonth, end: YearMonth): List<YearMonth> {
        val list = mutableListOf<YearMonth>()
        var cur = start
        while (cur <= end) {
            list.add(cur)
            cur = cur.plusMonths(1)
        }
        return list
    }

    private fun computeYearTotals(entries: Map<LocalDate, DayEntry>, year: Int): Map<ActivityType, Int> {
        val counts = ActivityType.entries.associateWith { 0 }.toMutableMap()
        entries.values
            .filter { it.date.year == year && it.activityType != null }
            .forEach { counts[it.activityType!!] = (counts[it.activityType] ?: 0) + 1 }
        return counts
    }

    private fun computeMonthCounts(entries: Map<LocalDate, DayEntry>, month: YearMonth): Map<ActivityType, Int> {
        val counts = ActivityType.entries.associateWith { 0 }.toMutableMap()
        entries.values
            .filter { YearMonth.from(it.date) == month && it.activityType != null }
            .forEach { counts[it.activityType!!] = (counts[it.activityType] ?: 0) + 1 }
        return counts
    }

    private fun computeStreak(entries: Map<LocalDate, DayEntry>, today: LocalDate): Int {
        var streak = 0
        var date = today
        while (true) {
            val entry = entries[date]
            val type = entry?.activityType
            if (type != null && type != ActivityType.DAY_OFF) {
                streak++
                date = date.minusDays(1)
            } else {
                break
            }
        }
        return streak
    }

    private fun computeLast10(entries: Map<LocalDate, DayEntry>, today: LocalDate): Int {
        var count = 0
        for (i in 0 until 10) {
            val date = today.minusDays(i.toLong())
            val type = entries[date]?.activityType
            if (type != null && type != ActivityType.DAY_OFF) count++
        }
        return count
    }
}
