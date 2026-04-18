package com.example.trackershmecker.ui.main.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.data.model.DayEntry
import com.example.trackershmecker.ui.theme.TextMuted
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MonthBlock(
    yearMonth: YearMonth,
    entries: Map<LocalDate, DayEntry>,
    isActive: Boolean,
    selectedDate: LocalDate?,
    today: LocalDate,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val alpha by animateFloatAsState(if (isActive) 1f else 0.4f, label = "monthAlpha")

    val firstDay = yearMonth.atDay(1)
    // Monday-based offset: Monday=0, Sunday=6
    val firstDowOffset = (firstDay.dayOfWeek.value - DayOfWeek.MONDAY.value + 7) % 7
    val daysInMonth = yearMonth.lengthOfMonth()

    // Build weeks
    val weeks = mutableListOf<List<Int?>>()
    var currentWeek = MutableList<Int?>(firstDowOffset) { null }
    for (d in 1..daysInMonth) {
        currentWeek.add(d)
        if (currentWeek.size == 7) {
            weeks.add(currentWeek)
            currentWeek = mutableListOf()
        }
    }
    if (currentWeek.isNotEmpty()) {
        while (currentWeek.size < 7) currentWeek.add(null)
        weeks.add(currentWeek)
    }

    Column(
        modifier = modifier
            .alpha(alpha)
            .padding(vertical = 16.dp),
    ) {
        // Month title
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = yearMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.5).sp,
            )
            if (yearMonth.monthValue == 1) {
                Text(
                    text = yearMonth.year.toString(),
                    fontSize = 13.sp,
                    color = TextMuted,
                    letterSpacing = 0.5.sp,
                )
            }
        }

        // Calendar grid
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            weeks.forEach { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    week.forEach { day ->
                        if (day != null) {
                            val date = yearMonth.atDay(day)
                            val entry = entries[date]
                            DayCell(
                                day = day,
                                activityType = entry?.activityType,
                                isToday = date == today,
                                isSelected = date == selectedDate,
                                hasNote = entry?.note != null,
                                isActive = isActive,
                                onClick = { onDayClick(date) },
                                modifier = Modifier.weight(1f),
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
