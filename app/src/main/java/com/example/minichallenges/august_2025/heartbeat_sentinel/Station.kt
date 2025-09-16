package com.example.minichallenges.august_2025.heartbeat_sentinel

import com.example.minichallenges.R

enum class Station(val timeElapsed: Int) {
    A(300),
    B(700),
    C(1000)
}

fun Station.getStringUi(): Int {
    return when (this) {
        Station.A -> R.string.heartbeat_sentinel_station_a
        Station.B -> R.string.heartbeat_sentinel_station_b
        Station.C -> R.string.heartbeat_sentinel_station_c
    }
}