package com.example.coroutines.and.minichallenges.august_2025.parce_pigeon_race

import com.example.coroutines.and.minichallenges.august_2025.parce_pigeon_race.Status.*

data class ParcePigeonRaceState(
    val status: Status = CAN_RUN,
    val images: MutableList<PigeonImage> = (0..5).map {
        PigeonImage(it)
    }.toMutableList()
)

enum class Status {
    CAN_RUN,
    RUNNING,
    CAN_RUN_AGAIN
}