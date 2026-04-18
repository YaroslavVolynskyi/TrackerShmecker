package com.example.trackershmecker.ui.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.ui.theme.Divider
import com.example.trackershmecker.ui.theme.TextMuted
import com.example.trackershmecker.ui.theme.TextPrimary
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DayNoteCard(
    date: LocalDate,
    activityTypes: List<ActivityType>,
    existingNote: String?,
    onSaveNote: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isEditing by remember(date) { mutableStateOf(false) }
    var draft by remember(date) { mutableStateOf(existingNote ?: "") }

    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .shadow(12.dp, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, Divider, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        Column {
            // Header row: date + activity badge + close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val dow = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                    val month = date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                    Text(
                        text = "$dow, $month ${date.dayOfMonth}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                    )
                    activityTypes.forEach { type ->
                        Text(
                            text = type.label.uppercase(),
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.3.sp,
                            color = type.cellFg,
                            modifier = Modifier
                                .background(type.cellColor, RoundedCornerShape(6.dp))
                                .padding(horizontal = 7.dp, vertical = 3.dp),
                        )
                    }
                }
                Text(
                    text = "\u2715",
                    fontSize = 16.sp,
                    color = TextMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onDismiss() },
                )
            }

            // Note content
            if (isEditing) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    BasicTextField(
                        value = draft,
                        onValueChange = { draft = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 6.dp),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 14.sp,
                            color = TextPrimary,
                        ),
                        decorationBox = { inner ->
                            Column {
                                if (draft.isEmpty()) {
                                    Text(
                                        text = "e.g. 80 kg, felt strong",
                                        fontSize = 14.sp,
                                        color = TextMuted,
                                    )
                                }
                                inner()
                                HorizontalDivider(color = Divider)
                            }
                        },
                    )
                    Button(
                        onClick = {
                            onSaveNote(draft)
                            isEditing = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TextPrimary,
                            contentColor = Color.White,
                        ),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text("Save", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            } else if (existingNote != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clickable {
                            draft = existingNote
                            isEditing = true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(text = "\uD83D\uDCDD", fontSize = 12.sp)
                    Text(text = existingNote, fontSize = 14.sp, color = TextPrimary)
                    Text(
                        text = "Tap to edit",
                        fontSize = 11.sp,
                        color = TextMuted,
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.End,
                    )
                }
            } else {
                TextButton(
                    onClick = {
                        draft = ""
                        isEditing = true
                    },
                    modifier = Modifier.padding(top = 4.dp),
                ) {
                    Text(
                        text = "+ Add note",
                        fontSize = 13.sp,
                        color = TextMuted,
                    )
                }
            }
        }
    }
}
