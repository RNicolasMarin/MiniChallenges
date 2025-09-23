package com.example.minichallenges.september_2025.map_chip_filter

import androidx.compose.ui.graphics.Color
import com.example.minichallenges.R
import com.example.minichallenges.september_2025.Lime
import com.example.minichallenges.september_2025.Orange
import com.example.minichallenges.september_2025.Pink
import com.example.minichallenges.september_2025.map_chip_filter.ChipFilter.*

sealed class ChipFilter(
    open val checked: Boolean,
    val optionNumber: Int,
    val textRes: Int,
    val iconRes: Int,
    val backgroundColor: Color
) {
    data class Stages(override val checked: Boolean): ChipFilter(checked, 0, R.string.map_chip_filter_stages, R.drawable.ic_stages, Lime)
    data class Food(override val checked: Boolean): ChipFilter(checked, 1, R.string.map_chip_filter_food, R.drawable.ic_food, Pink)
    data class Wc(override val checked: Boolean): ChipFilter(checked, 2, R.string.map_chip_filter_wc, R.drawable.ic_wc, Orange)
}

val chipFilters = listOf(Stages(false), Food(false), Wc(false))