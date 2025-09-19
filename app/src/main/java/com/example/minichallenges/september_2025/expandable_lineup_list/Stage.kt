package com.example.minichallenges.september_2025.expandable_lineup_list

import androidx.compose.ui.graphics.Color

data class Stage(
    val stageName: String,
    val color: Color,
    val performers: List<Performer>
)

data class Performer(
    val performerName: String,
    val time: String,
)
