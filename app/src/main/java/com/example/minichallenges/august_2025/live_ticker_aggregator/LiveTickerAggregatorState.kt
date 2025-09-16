package com.example.minichallenges.august_2025.live_ticker_aggregator

data class LiveTickerAggregatorState(
    val exchanges: List<Exchange> = emptyList(),
    val isRunning: Boolean = true,
    val isXETRABroken: Boolean = false,
    val updatesRate: Long = 250
)
