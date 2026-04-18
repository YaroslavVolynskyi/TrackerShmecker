package com.example.trackershmecker.ui.yeartotals

import androidx.lifecycle.SavedStateHandle
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
import java.time.Year
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class YearTotalsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: FitnessRepository,
) : ViewModel() {

    private val initialYear: Int = savedStateHandle.get<Int>("year") ?: LocalDate.now().year

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

    private fun computeState(entries: Map<LocalDate, DayEntry>) {
        val year = _uiState.value.year

        val yearEntries = entries.values.filter { it.date.year == year && it.activityTypes.isNotEmpty() }
        val prevYearEntries = entries.values.filter { it.date.year == year - 1 && it.activityTypes.isNotEmpty() }

        val totalWorkouts = yearEntries.count { entry -> entry.activityTypes.any { it != ActivityType.DAY_OFF } }
        val prevWorkouts = prevYearEntries.count { entry -> entry.activityTypes.any { it != ActivityType.DAY_OFF } }

        val activityCounts = ActivityType.entries.associateWith { type ->
            yearEntries.count { it.activityTypes.contains(type) }
        }

        val monthSummaries = (1..12).map { m ->
            val ym = YearMonth.of(year, m)
            val monthEntries = yearEntries.filter { it.date.monthValue == m }
            val workoutCount = monthEntries.count { entry -> entry.activityTypes.any { it != ActivityType.DAY_OFF } }
            val dayActivities = monthEntries
                .associate { it.date.dayOfMonth to it.activityTypes }
            MonthSummary(ym, workoutCount, dayActivities)
        }

        val totalDays = if (year == LocalDate.now().year) {
            LocalDate.now().dayOfYear
        } else {
            if (Year.of(year).isLeap) 366 else 365
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
}
