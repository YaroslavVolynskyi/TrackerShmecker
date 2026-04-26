package com.example.trackershmecker.ui.main.components

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.ui.theme.Divider
import com.example.trackershmecker.ui.theme.TextMuted
import com.example.trackershmecker.ui.theme.TextPrimary
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

private val DarkPurple = Color(0xFF5E35B1)

@Composable
fun DayNoteCard(
    date: LocalDate,
    activityTypes: List<ActivityType>,
    existingNote: String?,
    onSaveNote: (String) -> Unit,
    onDeleteNote: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val initialText = existingNote ?: ""
    var draft by remember(date) {
        mutableStateOf(TextFieldValue(initialText, TextRange(initialText.length)))
    }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(date) {
        focusRequester.requestFocus()
    }

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

            // Note editor
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                BasicTextField(
                    value = draft,
                    onValueChange = { draft = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .padding(vertical = 6.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 14.sp,
                        color = TextPrimary,
                    ),
                    decorationBox = { inner ->
                        Column {
                            inner()
                            HorizontalDivider(color = Divider)
                        }
                    },
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    if (existingNote != null) {
                        Button(
                            onClick = { onDeleteNote() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkPurple,
                                contentColor = Color.White,
                            ),
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text("Delete", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Button(
                        onClick = { onSaveNote(draft.text) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkPurple,
                            contentColor = Color.White,
                        ),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text("Save", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
