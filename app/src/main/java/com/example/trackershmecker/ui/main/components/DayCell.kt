package com.example.trackershmecker.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.ui.theme.Background
import com.example.trackershmecker.ui.theme.EmptyCell
import com.example.trackershmecker.ui.theme.SelectedRing
import com.example.trackershmecker.ui.theme.TextMuted
import com.example.trackershmecker.ui.theme.TodayRing

@Composable
fun DayCell(
    day: Int,
    activityType: ActivityType?,
    isToday: Boolean,
    isSelected: Boolean,
    hasNote: Boolean,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bgColor = activityType?.cellColor ?: EmptyCell
    val fgColor = activityType?.cellFg ?: TextMuted

    Box(
        modifier = modifier
            .aspectRatio(1f / 1.15f)
            .drawBehind {
                if (isToday) {
                    // Outer ring in background color (gap)
                    drawRoundRect(
                        color = Background,
                        topLeft = Offset(-4.dp.toPx(), -4.dp.toPx()),
                        size = Size(size.width + 8.dp.toPx(), size.height + 8.dp.toPx()),
                        cornerRadius = CornerRadius(12.dp.toPx()),
                        style = Stroke(width = 4.dp.toPx()),
                    )
                    // Inner ring in dark color
                    drawRoundRect(
                        color = TodayRing,
                        topLeft = Offset(-2.dp.toPx(), -2.dp.toPx()),
                        size = Size(size.width + 4.dp.toPx(), size.height + 4.dp.toPx()),
                        cornerRadius = CornerRadius(11.dp.toPx()),
                        style = Stroke(width = 2.dp.toPx()),
                    )
                } else if (isSelected) {
                    drawRoundRect(
                        color = Background,
                        topLeft = Offset(-4.dp.toPx(), -4.dp.toPx()),
                        size = Size(size.width + 8.dp.toPx(), size.height + 8.dp.toPx()),
                        cornerRadius = CornerRadius(12.dp.toPx()),
                        style = Stroke(width = 4.dp.toPx()),
                    )
                    drawRoundRect(
                        color = SelectedRing,
                        topLeft = Offset(-2.dp.toPx(), -2.dp.toPx()),
                        size = Size(size.width + 4.dp.toPx(), size.height + 4.dp.toPx()),
                        cornerRadius = CornerRadius(11.dp.toPx()),
                        style = Stroke(width = 2.dp.toPx()),
                    )
                }
            }
            .background(bgColor, RoundedCornerShape(10.dp))
            .clickable(enabled = isActive) { onClick() }
            .padding(horizontal = 4.dp, vertical = 5.dp),
    ) {
        Column(modifier = Modifier.matchParentSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Text(
                    text = day.toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = fgColor,
                    lineHeight = 12.sp,
                )
                if (hasNote) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .background(
                                if (activityType != null) Color.White.copy(alpha = 0.9f)
                                else Color(0xFF4A4032),
                                CircleShape,
                            ),
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            if (activityType != null) {
                Text(
                    text = activityType.label.uppercase(),
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = fgColor.copy(alpha = 0.95f),
                    letterSpacing = 0.3.sp,
                    lineHeight = 8.5.sp,
                )
            }
        }
    }
}
