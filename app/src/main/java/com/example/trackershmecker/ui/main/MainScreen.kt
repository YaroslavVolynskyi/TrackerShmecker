package com.example.trackershmecker.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.trackershmecker.data.model.ActivityType
import com.example.trackershmecker.ui.main.components.ActivityButtonBar
import com.example.trackershmecker.ui.main.components.CalendarHeader
import com.example.trackershmecker.ui.main.components.DayNoteCard
import com.example.trackershmecker.ui.main.components.MonthBlock
import com.example.trackershmecker.ui.main.components.StreakLine
import com.example.trackershmecker.ui.main.components.WeekdayRow
import com.example.trackershmecker.ui.theme.Background
import java.time.YearMonth

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    targetMonth: YearMonth? = null,
    onYearTap: (Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val currentMonthIndex = remember(state.months) {
        state.months.indexOf(YearMonth.from(state.today))
    }
    val isAwayFromCurrentMonth by remember {
        derivedStateOf {
            if (currentMonthIndex < 0) false
            else state.activeMonth != YearMonth.from(state.today)
        }
    }

    // Ensure target month is in the list and scroll to it
    LaunchedEffect(targetMonth) {
        if (targetMonth != null) {
            viewModel.ensureMonthVisible(targetMonth)
        }
    }

    // Scroll to target month or today's month on first composition
    LaunchedEffect(state.months, targetMonth) {
        if (state.months.isEmpty()) return@LaunchedEffect
        val scrollTarget = targetMonth ?: YearMonth.from(state.today)
        val idx = state.months.indexOf(scrollTarget)
        if (idx >= 0) {
            listState.scrollToItem(idx)
        }
    }

    // Active month detection
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val viewportHeight = layoutInfo.viewportSize.height.toFloat()
                if (viewportHeight <= 0) return@collect
                var bestIdx = -1
                var bestFraction = 0f
                layoutInfo.visibleItemsInfo.forEach { item ->
                    val top = maxOf(0, item.offset)
                    val bottom = minOf(viewportHeight.toInt(), item.offset + item.size)
                    val visible = (bottom - top).toFloat()
                    val fraction = if (item.size > 0) visible / item.size else 0f
                    if (fraction > bestFraction) {
                        bestFraction = fraction
                        bestIdx = item.index
                    }
                }
                if (bestIdx >= 0 && bestIdx < state.months.size) {
                    val month = state.months[bestIdx]
                    if (month != state.activeMonth) {
                        viewModel.onActiveMonthChanged(month)
                    }
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding(),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Fixed header
            CalendarHeader(
                year = state.activeMonth.year,
                yearTotals = state.yearTotals,
                onYearTap = onYearTap,
            )

            // Fixed weekday row
            WeekdayRow(selectedDate = state.selectedDate)

            // Scrollable month stack
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                itemsIndexed(state.months, key = { _, m -> m }) { index, month ->
                    MonthBlock(
                        yearMonth = month,
                        entries = state.entries,
                        isActive = month == state.activeMonth,
                        selectedDate = state.selectedDate,
                        today = state.today,
                        onDayClick = { date ->
                            viewModel.onDaySelected(date)
                        },
                    )
                }
                // Bottom spacer so content doesn't hide behind buttons + streak line
                item {
                    Spacer(modifier = Modifier.height(180.dp))
                }
            }
        }

        // Day note card overlay
        AnimatedVisibility(
            visible = state.selectedDate != null,
            enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 180.dp)
                .navigationBarsPadding(),
        ) {
            state.selectedDate?.let { date ->
                val entry = state.entries[date]
                DayNoteCard(
                    date = date,
                    activityTypes = entry?.activityTypes ?: emptyList(),
                    existingNote = entry?.note,
                    onSaveNote = { note -> viewModel.onNoteUpdated(date, note) },
                    onDismiss = { viewModel.onDaySelected(null) },
                )
            }
        }

        // Scroll to current month FAB
        AnimatedVisibility(
            visible = isAwayFromCurrentMonth,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 200.dp)
                .navigationBarsPadding(),
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0x997B1FA2), CircleShape)
                    .clickable {
                        if (currentMonthIndex >= 0) {
                            scope.launch {
                                listState.animateScrollToItem(currentMonthIndex)
                            }
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "↓",
                    color = Color.White,
                    fontSize = 22.sp,
                )
            }
        }

        // Gradient fade above buttons
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(140.dp)
                .navigationBarsPadding()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Background),
                        startY = 0f,
                        endY = 80f,
                    ),
                ),
        )

        // Fixed activity buttons with streak line
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .background(
                    Color(0x337B1FA2),
                    RoundedCornerShape(20.dp),
                )
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (state.activeMonth == YearMonth.from(state.today)) {
                StreakLine(
                    streakCount = state.streakCount,
                    last10Active = state.last10Active,
                )
            }
            val todayActivities = state.entries[state.today]?.activityTypes
                ?.toSet()
                ?.ifEmpty { setOf(ActivityType.DAY_OFF) }
                ?: setOf(ActivityType.DAY_OFF)
            ActivityButtonBar(
                counts = state.monthCounts,
                selectedActivities = todayActivities,
                onActivityClick = { viewModel.onActivityLogged(it) },
                onActivityLongClick = { viewModel.onActivityAdded(it) },
            )
        }
    }
}
