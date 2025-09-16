package com.example.minichallenges.august_2025.live_ticker_aggregator

sealed interface LiveTickerAggregatorAction {

    data object OnPauseResume: LiveTickerAggregatorAction

    data object OnBreakXETRA: LiveTickerAggregatorAction
}