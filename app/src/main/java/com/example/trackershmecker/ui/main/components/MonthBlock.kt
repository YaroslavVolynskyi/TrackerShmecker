package com.example.trackershmecker.ui.main.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.data.model.DayEntry
import com.example.trackershmecker.ui.theme.TextMuted
import com.example.trackershmecker.ui.theme.TextSecondary
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
        // Month title with activity counts
        val monthCounts = mutableMapOf<ActivityType, Int>()
        entries.values
            .filter { YearMonth.from(it.date) == yearMonth }
            .forEach { entry ->
                entry.activityTypes.forEach { type ->
                    monthCounts[type] = (monthCounts[type] ?: 0) + 1
                }
            }
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .background(
                    if (yearMonth.monthValue == 1) Color(0x557B1FA2) else Color(0x337B1FA2),
                    RoundedCornerShape(12.dp),
                )
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
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
            ActivityType.entries.forEach { activity ->
                val count = monthCounts[activity] ?: 0
                if (count > 0 || activity == ActivityType.GYM) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(activity.dotColor, CircleShape),
                        )
                        Text(
                            text = activity.label,
                            fontSize = 16.sp,
                            color = TextMuted,
                        )
                        Text(
                            text = count.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextSecondary,
                        )
                    }
                }
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
                                activityTypes = entry?.activityTypes ?: emptyList(),
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
