package com.example.trackershmecker.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.ui.theme.Background
import com.example.trackershmecker.ui.theme.Divider
import com.example.trackershmecker.ui.theme.TextMuted
import com.example.trackershmecker.ui.theme.SelectedRing
import com.example.trackershmecker.ui.theme.TodayRing
import androidx.compose.foundation.layout.Column
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun WeekdayRow(
    today: LocalDate = LocalDate.now(),
    selectedDate: LocalDate? = null,
    modifier: Modifier = Modifier,
) {
    val todayDowIndex = (today.dayOfWeek.value - DayOfWeek.MONDAY.value + 7) % 7
    val selectedDowIndex = selectedDate?.let {
        (it.dayOfWeek.value - DayOfWeek.MONDAY.value + 7) % 7
    }

    Column(modifier = modifier.background(Background)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
        ) {
            val days = listOf("M", "T", "W", "T", "F", "S", "S")
            days.forEachIndexed { index, day ->
                val isTodayDow = index == todayDowIndex
                val isSelectedDow = selectedDowIndex == index && !isTodayDow
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    if (isTodayDow) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(TodayRing, CircleShape),
                        )
                    } else if (isSelectedDow) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(SelectedRing, CircleShape),
                        )
                    }
                    Text(
                        text = day,
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp,
                        fontWeight = if (isTodayDow || isSelectedDow) FontWeight.Bold else FontWeight.Medium,
                        color = when {
                            isTodayDow -> Background
                            isSelectedDow -> Background
                            else -> TextMuted
                        },
                    )
                }
            }
        }
        HorizontalDivider(color = Divider, thickness = 1.dp)
    }
}
