package com.example.trackershmecker.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.ui.theme.Divider
import com.example.trackershmecker.ui.theme.TextMuted
import com.example.trackershmecker.ui.theme.TextSecondary

@Composable
fun StreakLine(
    streakCount: Int,
    last10Active: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(text = "\uD83D\uDD25", fontSize = 13.sp)
            Text(
                text = "$streakCount",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
            )
            Text(
                text = "day streak",
                fontSize = 12.sp,
                color = TextSecondary,
            )
        }

        Box(
            modifier = Modifier
                .width(1.dp)
                .height(14.dp)
                .background(Divider),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "$last10Active/10",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
            )
            Text(
                text = "last 10 days",
                fontSize = 12.sp,
                color = TextMuted,
            )
        }
    }
}
