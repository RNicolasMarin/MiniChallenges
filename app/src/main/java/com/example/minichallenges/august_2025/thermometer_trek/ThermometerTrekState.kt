package com.example.minichallenges.august_2025.thermometer_trek

import com.example.minichallenges.august_2025.thermometer_trek.Status.*

data class ThermometerTrekState(
    val temperatures : List<Double> = emptyList(),
    val status: Status = CAN_START
)

enum class Status {
    CAN_START,
    TRACKING,
    CAN_RESET
}
