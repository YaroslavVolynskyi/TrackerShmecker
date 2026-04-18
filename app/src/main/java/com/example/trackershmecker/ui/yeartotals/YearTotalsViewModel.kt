package com.example.trackershmecker.ui.yeartotals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.DayEntry
import com.example.trackershmecker.data.repository.FitnessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth

class YearTotalsViewModel(
    initialYear: Int,
    private val repository: FitnessRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(YearTotalsUiState(year = initialYear))
    val uiState: StateFlow<YearTotalsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getEntries().collect { entries ->
                computeState(entries)
            }
        }
    }

    fun onPreviousYear() {
        _uiState.update { it.copy(year = it.year - 1) }
        viewModelScope.launch {
            repository.getEntries().collect { entries ->
                computeState(entries)
            }
        }
    }

    fun onNextYear() {
        _uiState.update { it.copy(year = it.year + 1) }
        viewModelScope.launch {
            repository.getEntries().collect { entries ->
                computeState(entries)
            }
        }
    }

    fun setYear(year: Int) {
        _uiState.update { it.copy(year = year) }
        viewModelScope.launch {
            repository.getEntries().collect { entries ->
                computeState(entries)
            }
        }
    }

    private fun computeState(entries: Map<java.time.LocalDate, DayEntry>) {
        val year = _uiState.value.year

        val yearEntries = entries.values.filter { it.date.year == year && it.activityType != null }
        val prevYearEntries = entries.values.filter { it.date.year == year - 1 && it.activityType != null }

        val totalWorkouts = yearEntries.count { it.activityType != ActivityType.DAY_OFF }
        val prevWorkouts = prevYearEntries.count { it.activityType != ActivityType.DAY_OFF }

        val activityCounts = ActivityType.entries.associateWith { type ->
            yearEntries.count { it.activityType == type }
        }

        val monthSummaries = (1..12).map { m ->
            val ym = YearMonth.of(year, m)
            val monthEntries = yearEntries.filter { it.date.monthValue == m }
            val workoutCount = monthEntries.count { it.activityType != ActivityType.DAY_OFF }
            val dayActivities = monthEntries
                .filter { it.activityType != null }
                .associate { it.date.dayOfMonth to it.activityType!! }
            MonthSummary(ym, workoutCount, dayActivities)
        }

        val totalDays = if (year == java.time.LocalDate.now().year) {
            java.time.LocalDate.now().dayOfYear
        } else {
            if (java.time.Year.of(year).isLeap) 366 else 365
        }
        val activeDays = yearEntries.size
        val pct = if (totalDays > 0) (activeDays * 100) / totalDays else 0

        _uiState.update {
            it.copy(
                totalWorkouts = totalWorkouts,
                previousYearWorkouts = prevWorkouts,
                activityCounts = activityCounts,
                monthSummaries = monthSummaries,
                activeDaysPercent = pct,
            )
        }
    }

    class Factory(private val year: Int, private val repository: FitnessRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return YearTotalsViewModel(year, repository) as T
        }
    }
}
