package com.example.minichallenges

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.minichallenges.august_2025.heartbeat_sentinel.HeartbeatSentinelRoot
import com.example.minichallenges.august_2025.live_ticker_aggregator.LiveTickerAggregatorRoot
import com.example.minichallenges.august_2025.live_ticker_aggregator.LiveTickerAggregatorViewModel
import com.example.minichallenges.august_2025.order_queue_outpost.OrderQueueOutpostRoot
import com.example.minichallenges.august_2025.parce_pigeon_race.ParcePigeonRaceRoot
import com.example.minichallenges.august_2025.thermometer_trek.ThermometerTrekRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    liveTickerAggregatorViewModel: LiveTickerAggregatorViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ChallengeSelector
    ) {
        composable<Screen.ChallengeSelector> {
            val challenges = listOf(
                Challenge(
                    number = 1,
                    nameRes = R.string.thermometer_trek_challenge_name,
                    topicsRes = R.string.thermometer_trek_challenge_topics,
                    descriptionRes = R.string.thermometer_trek_challenge_description,
                    screen = Screen.ThermometerTrek
                ),
                Challenge(
                    number = 2,
                    nameRes = R.string.order_queue_outpost_challenge_name,
                    topicsRes = R.string.order_queue_outpost_challenge_topics,
                    descriptionRes = R.string.order_queue_outpost_challenge_description,
                    screen = Screen.OrderQueueOutpost
                ),
                Challenge(
                    number = 3,
                    nameRes = R.string.parce_pigeon_race_challenge_name,
                    topicsRes = R.string.parce_pigeon_race_challenge_topics,
                    descriptionRes = R.string.parce_pigeon_race_challenge_description,
                    screen = Screen.ParcePigeonRace
                ),
                Challenge(
                    number = 4,
                    nameRes = R.string.heartbeat_sentinel_challenge_name,
                    topicsRes = R.string.heartbeat_sentinel_challenge_topics,
                    descriptionRes = R.string.heartbeat_sentinel_challenge_description,
                    screen = Screen.HeartbeatSentinel
                ),
                Challenge(
                    number = 5,
                    nameRes = R.string.live_ticker_aggregator_challenge_name,
                    topicsRes = R.string.live_ticker_aggregator_challenge_topics,
                    descriptionRes = R.string.live_ticker_aggregator_challenge_description,
                    screen = Screen.LiveTickerAggregator
                )
            )
            ChallengeSelector(
                challenges = challenges,
                title = R.string.mini_challenges_august_2025,
                onClick = { screen ->
                    navController.navigate(
                        screen
                    )
                }
            )
        }
        composable<Screen.ThermometerTrek> {
            ThermometerTrekRoot()
        }
        composable<Screen.OrderQueueOutpost> {
            OrderQueueOutpostRoot()
        }
        composable<Screen.ParcePigeonRace> {
            ParcePigeonRaceRoot()
        }
        composable<Screen.HeartbeatSentinel> {
            HeartbeatSentinelRoot()
        }
        composable<Screen.LiveTickerAggregator> {
            LiveTickerAggregatorRoot(
                viewModel = liveTickerAggregatorViewModel
            )
        }
    }
}