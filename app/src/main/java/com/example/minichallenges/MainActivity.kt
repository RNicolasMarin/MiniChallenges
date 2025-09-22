package com.example.minichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.minichallenges.august_2025.live_ticker_aggregator.LiveTickerAggregatorViewModel
import com.example.minichallenges.september_2025.expandable_lineup_list.ExpandableLineupList
import com.example.minichallenges.september_2025.ticket_builder.TicketBuilder
import com.example.minichallenges.ui.theme.MiniChallengesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniChallengesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /*val viewmodel by viewModels<LiveTickerAggregatorViewModel>()
                    NavigationRoot(
                        navController = rememberNavController(),
                        liveTickerAggregatorViewModel = viewmodel
                    )*/
                    TicketBuilder()
                }
            }
        }
    }
}