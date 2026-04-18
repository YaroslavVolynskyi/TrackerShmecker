package com.example.trackershmecker.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.ui.theme.Background
import com.example.trackershmecker.ui.theme.Divider
import com.example.trackershmecker.ui.theme.TextMuted
import androidx.compose.foundation.layout.Column

@Composable
fun WeekdayRow(modifier: Modifier = Modifier) {
    Column(modifier = modifier.background(Background)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
        ) {
            val days = listOf("M", "T", "W", "T", "F", "S", "S")
            days.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextMuted,
                )
            }
        }
        HorizontalDivider(color = Divider, thickness = 1.dp)
    }
}
