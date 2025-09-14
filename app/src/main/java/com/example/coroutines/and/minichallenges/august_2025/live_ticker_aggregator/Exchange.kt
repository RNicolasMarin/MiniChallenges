package com.example.coroutines.and.minichallenges.august_2025.live_ticker_aggregator

data class Exchange(
    val name: String,
    val initialPrice: Double,
    val currentPrice: Double = initialPrice,
    val lastTimeUpdated: Long = 0,
    val updateDelayMs: Long
)