package com.example.minichallenges

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object ChallengeSelector: Screen()

    @Serializable
    data object ThermometerTrek: Screen()

    @Serializable
    data object OrderQueueOutpost: Screen()

    @Serializable
    data object ParcePigeonRace: Screen()

    @Serializable
    data object HeartbeatSentinel: Screen()

    @Serializable
    data object LiveTickerAggregator: Screen()
}