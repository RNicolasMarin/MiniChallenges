package com.example.coroutines.and.minichallenges.august_2025.parce_pigeon_race

import com.example.coroutines.and.minichallenges.august_2025.parce_pigeon_race.Status.*

data class ParcePigeonRaceState(
    val status: Status = CAN_RUN,
)

enum class Status {
    CAN_RUN,
    RUNNING,
    CAN_RUN_AGAIN
}