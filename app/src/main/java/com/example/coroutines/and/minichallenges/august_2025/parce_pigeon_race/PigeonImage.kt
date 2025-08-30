package com.example.coroutines.and.minichallenges.august_2025.parce_pigeon_race

import java.io.File

data class PigeonImage(
    val position: Int,
    val file: File? = null,
    val size: Double = 0.0,
    val durationInMilSeconds: Long = 0L,
    val isLoading: Boolean = true
)
