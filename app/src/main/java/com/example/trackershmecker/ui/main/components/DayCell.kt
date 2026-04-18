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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.ui.theme.Background
import com.example.trackershmecker.ui.theme.EmptyCell
import com.example.trackershmecker.ui.theme.SelectedRing
import com.example.trackershmecker.ui.theme.TextMuted
import com.example.trackershmecker.ui.theme.TodayRing
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

@Composable
fun DayCell(
    day: Int,
    activityTypes: List<ActivityType>,
    isToday: Boolean,
    isSelected: Boolean,
    hasNote: Boolean,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val fgColor = when {
        activityTypes.isEmpty() -> TextMuted
        else -> activityTypes.first().cellFg
    }

    Box(
        modifier = modifier
            .aspectRatio(1f / 1.15f)
            .drawBehind {
                if (isToday) {
                    drawRoundRect(
                        color = Background,
                        topLeft = Offset(-4.dp.toPx(), -4.dp.toPx()),
                        size = Size(size.width + 8.dp.toPx(), size.height + 8.dp.toPx()),
                        cornerRadius = CornerRadius(12.dp.toPx()),
                        style = Stroke(width = 4.dp.toPx()),
                    )
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
            .clip(RoundedCornerShape(10.dp))
            .drawBehind {
                drawMultiActivityBackground(activityTypes)
            }
            .clickable { onClick() }
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
                                if (activityTypes.isNotEmpty()) Color.White.copy(alpha = 0.9f)
                                else Color(0xFF4A4032),
                                CircleShape,
                            ),
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            if (activityTypes.size == 1) {
                Text(
                    text = activityTypes.first().label.uppercase(),
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

private fun DrawScope.drawMultiActivityBackground(activityTypes: List<ActivityType>) {
    when (activityTypes.size) {
        0 -> drawRect(EmptyCell)
        1 -> drawRect(activityTypes[0].cellColor)
        2 -> {
            // Diagonal split: top-left = first, bottom-right = second
            drawRect(activityTypes[0].cellColor)
            val path = Path().apply {
                moveTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path, activityTypes[1].cellColor)
        }
        else -> {
            // Mercedes/Y split: 3 sectors at 120° each
            val cx = size.width / 2
            val cy = size.height / 2
            val r = max(size.width, size.height) * 2

            for (i in 0 until minOf(3, activityTypes.size)) {
                val startAngle = i * 2.0 * Math.PI / 3.0
                val endAngle = (i + 1) * 2.0 * Math.PI / 3.0

                val path = Path().apply {
                    moveTo(cx, cy)
                    val steps = 12
                    for (s in 0..steps) {
                        val angle = startAngle + (endAngle - startAngle) * s / steps
                        lineTo(
                            cx + (r * sin(angle)).toFloat(),
                            cy - (r * cos(angle)).toFloat(),
                        )
                    }
                    close()
                }
                drawPath(path, activityTypes[i].cellColor)
            }
        }
    }
}
