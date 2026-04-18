package com.example.trackershmecker.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.ui.theme.Background
import com.example.trackershmecker.ui.theme.TextMuted
import com.example.trackershmecker.ui.theme.TextPrimary
import com.example.trackershmecker.ui.theme.TextSecondary

@Composable
fun CalendarHeader(
    year: Int,
    yearTotals: Map<ActivityType, Int>,
    onYearTap: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(Background)
            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = year.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            letterSpacing = 0.5.sp,
            modifier = Modifier
                .background(Color(0x557B1FA2), RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .clickable { onYearTap(year) },
        )
        Row(
            modifier = Modifier
                .background(Color(0x557B1FA2), RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ActivityType.entries.forEach { activity ->
                val count = yearTotals[activity] ?: 0
                if (count > 0 || activity == ActivityType.GYM) Row(
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
                        fontSize = 14.sp,
                        color = TextPrimary,
                    )
                    Text(
                        text = (yearTotals[activity] ?: 0).toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                    )
                }
            }
        }
    }
}
