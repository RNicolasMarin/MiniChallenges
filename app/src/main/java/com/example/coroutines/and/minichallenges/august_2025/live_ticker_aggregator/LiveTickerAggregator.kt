package com.example.coroutines.and.minichallenges.august_2025.live_ticker_aggregator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@Composable
fun LiveTickerAggregator(
    modifier: Modifier = Modifier,
    viewModel: LiveTickerAggregatorViewModel
) {
    val a = viewModel.feeds.collectAsState()
    Text(text = a.value.toString())
}