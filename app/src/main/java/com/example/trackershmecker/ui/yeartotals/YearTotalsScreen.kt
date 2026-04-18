package com.example.trackershmecker.ui.yeartotals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.ui.theme.Background
import com.example.trackershmecker.ui.theme.EmptyCell
import com.example.trackershmecker.ui.theme.TextMuted
import com.example.trackershmecker.ui.theme.TextPrimary
import com.example.trackershmecker.ui.theme.TextSecondary
import com.example.trackershmecker.ui.theme.Divider
import java.time.DayOfWeek
import java.time.YearMonth

@Composable
fun YearTotalsScreen(
    viewModel: YearTotalsViewModel,
    onBack: () -> Unit,
    onPickMonth: (Int, Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val delta = state.totalWorkouts - state.previousYearWorkouts
    val hasPrev = state.previousYearWorkouts > 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        // Top section: back + hero + pills
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
            // Back button
            Text(
                text = "\u2039 ${state.year}",
                fontSize = 13.sp,
                color = TextMuted,
                letterSpacing = 0.5.sp,
                modifier = Modifier.clickable { onBack() },
            )

            // Hero: big workout count
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = state.totalWorkouts.toString(),
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    letterSpacing = (-2).sp,
                    lineHeight = 56.sp,
                )
                Column(modifier = Modifier.padding(bottom = 6.dp)) {
                    Text(
                        text = "workouts",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary,
                    )
                    if (hasPrev) {
                        Text(
                            text = "${if (delta >= 0) "+" else ""}$delta vs ${state.year - 1}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (delta >= 0) Color(0xFF3DBF2E) else Color(0xFFDB5030),
                        )
                    }
                }
            }

            // Activity pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                ActivityType.entries.forEach { activity ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(activity.cellColor, RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = activity.label.uppercase(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = activity.cellFg.copy(alpha = 0.9f),
                            letterSpacing = 0.3.sp,
                        )
                        Text(
                            text = (state.activityCounts[activity] ?: 0).toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = activity.cellFg,
                            letterSpacing = (-0.5).sp,
                            lineHeight = 24.sp,
                        )
                    }
                }
            }
        }

        // Year grid: 3 cols x 4 rows
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (row in 0 until 4) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    for (col in 0 until 3) {
                        val monthIdx = row * 3 + col
                        val summary = state.monthSummaries.getOrNull(monthIdx)
                        if (summary != null) {
                            MiniMonthCard(
                                summary = summary,
                                onClick = { onPickMonth(state.year, monthIdx + 1) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            }
        }

        // Bottom nav: year arrows + active days
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Divider, CircleShape)
                    .clip(CircleShape)
                    .clickable { viewModel.setYear(state.year - 1) },
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "\u2039", fontSize = 14.sp, color = TextPrimary)
            }
            Text(
                text = "${state.activeDaysPercent}% active days",
                fontSize = 11.sp,
                color = TextMuted,
                letterSpacing = 0.3.sp,
                modifier = Modifier.padding(horizontal = 14.dp),
            )
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Divider, CircleShape)
                    .clip(CircleShape)
                    .clickable { viewModel.setYear(state.year + 1) },
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "\u203A", fontSize = 14.sp, color = TextPrimary)
            }
        }
    }
}

@Composable
private fun MiniMonthCard(
    summary: MonthSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val hasData = summary.workoutCount > 0
    val monthNames = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    Column(
        modifier = modifier
            .background(
                if (hasData) Color.White.copy(alpha = 0.55f) else Color.White.copy(alpha = 0.25f),
                RoundedCornerShape(10.dp),
            )
            .clickable { onClick() }
            .padding(6.dp),
    ) {
        // Month label + count
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = monthNames[summary.yearMonth.monthValue - 1],
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                letterSpacing = 0.2.sp,
            )
            if (hasData) {
                Text(
                    text = summary.workoutCount.toString(),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                )
            }
        }

        // Mini dot grid
        val ym = summary.yearMonth
        val firstDow = (ym.atDay(1).dayOfWeek.value - DayOfWeek.MONDAY.value + 7) % 7
        val daysInMonth = ym.lengthOfMonth()

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(1.5.dp),
        ) {
            var dayCounter = 1 - firstDow
            while (dayCounter <= daysInMonth) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(1.5.dp),
                ) {
                    for (col in 0 until 7) {
                        val d = dayCounter + col
                        if (d in 1..daysInMonth) {
                            val act = summary.dayActivities[d]
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        act?.cellColor ?: EmptyCell,
                                        RoundedCornerShape(2.dp),
                                    )
                                    .then(
                                        // Force aspect ratio roughly 1:1
                                        Modifier.size(width = 0.dp, height = 6.dp)
                                    ),
                            )
                        } else {
                            Box(modifier = Modifier.weight(1f).size(width = 0.dp, height = 6.dp))
                        }
                    }
                }
                dayCounter += 7
            }
        }
    }
}
