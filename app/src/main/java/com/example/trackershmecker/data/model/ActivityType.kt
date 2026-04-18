package com.example.trackershmecker.data.model

import androidx.compose.ui.graphics.Color

enum class ActivityType(
    val label: String,
    val emoji: String,
    val cellColor: Color,
    val cellFg: Color,
    val buttonBg: Color,
    val buttonFg: Color,
    val buttonBorder: Color,
    val dotColor: Color = cellColor,
    val borderColor: Color = buttonBorder
) {
    GYM(
        label = "gym",
        emoji = "\uD83C\uDFCB\uFE0F",
        cellColor = Color(0xFFAA30C8),
        cellFg = Color.White,
        buttonBg = Color(0x73AA30C8),
        buttonFg = Color(0xFF5A0A6E),
        buttonBorder = Color(0x8CAA30C8),
    ),
    TENNIS(
        label = "tennis",
        emoji = "\uD83C\uDFBE",
        cellColor = Color(0xFF3DBF2E),
        cellFg = Color.White,
        buttonBg = Color(0x733DBF2E),
        buttonFg = Color(0xFF1A5A12),
        buttonBorder = Color(0x8C3DBF2E),
    ),
    POOL(
        label = "pool",
        emoji = "\uD83C\uDFCA",
        cellColor = Color(0xFF007FDB),
        cellFg = Color.White,
        buttonBg = Color(0x73007FDB),
        buttonFg = Color(0xFF003A66),
        buttonBorder = Color(0x8C007FDB),
    ),
    DAY_OFF(
        label = "day off",
        emoji = "\uD83D\uDCA4",
        cellColor = Color(0x8CD4AAA0),
        cellFg = Color(0xFF6B3A30),
        buttonBg = Color(0x73D4AAA0),
        buttonFg = Color(0xFF6B3A30),
        buttonBorder = Color(0x99D4AAA0),
        dotColor = Color(0x8CB08070),
        borderColor = Color(0xFF6B3A30)
    );
}
