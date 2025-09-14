package com.example.coroutines.and.minichallenges.august_2025.live_ticker_aggregator

data class LiveTickerAggregatorState(
    val exchanges: List<Exchange> = emptyList(),
    val isRunning: Boolean = true,
    val updatesRate: Long = 250
)
