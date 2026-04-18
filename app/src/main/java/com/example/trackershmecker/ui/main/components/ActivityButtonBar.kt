package com.example.trackershmecker.ui.main.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackershmecker.data.model.ActivityType
import kotlinx.coroutines.launch

@Composable
fun ActivityButtonBar(
    counts: Map<ActivityType, Int>,
    onActivityClick: (ActivityType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ActivityType.entries.forEach { activity ->
            val scale = remember { Animatable(1f) }
            val scope = rememberCoroutineScope()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(88.dp)
                    .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
                    .background(activity.buttonBg, RoundedCornerShape(16.dp))
                    .border(1.dp, activity.buttonBorder, RoundedCornerShape(16.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        scope.launch {
                            scale.animateTo(0.96f, spring(dampingRatio = 0.5f, stiffness = 800f))
                            scale.animateTo(1f, spring(dampingRatio = 0.5f, stiffness = 400f))
                        }
                        onActivityClick(activity)
                    }
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = activity.emoji, fontSize = 18.sp, lineHeight = 18.sp)
                Text(
                    text = activity.label,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = activity.buttonFg,
                    letterSpacing = 0.1.sp,
                )
                Text(
                    text = (counts[activity] ?: 0).toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = activity.buttonFg,
                    letterSpacing = (-0.3).sp,
                )
            }
        }
    }
}
