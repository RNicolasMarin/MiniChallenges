package com.example.minichallenges

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.minichallenges.august_2025.heartbeat_sentinel.HeartbeatSentinelRoot
import com.example.minichallenges.august_2025.live_ticker_aggregator.LiveTickerAggregatorRoot
import com.example.minichallenges.august_2025.order_queue_outpost.OrderQueueOutpostRoot
import com.example.minichallenges.august_2025.parce_pigeon_race.ParcePigeonRaceRoot
import com.example.minichallenges.august_2025.thermometer_trek.ThermometerTrekRoot
import com.example.minichallenges.september_2025.accessible_audio_schedule.AccessibleAudioSchedule
import com.example.minichallenges.september_2025.expandable_lineup_list.ExpandableLineupList
import com.example.minichallenges.september_2025.map_chip_filter.MapChipFilter
import com.example.minichallenges.september_2025.multi_stage_timeline_painter.MultiStageTimelinePainter
import com.example.minichallenges.september_2025.ticket_builder.TicketBuilder

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ChallengeSelector
    ) {
        composable<Screen.ChallengeSelector> {
            val challenges = listOf(
                Challenge(
                    number = 1,
                    name = "Expandable Lineup List",
                    topics = "[Expandable Lists · State Management · UI]",
                    description = "This challenge simulates a mobile app that displays a music festival lineup, allowing users to view scheduled performers by stage.",
                    screen = Screen.ExpandableLineupList
                ),
                Challenge(
                    number = 2,
                    name = "Ticket Builder",
                    topics = "[Dynamic Updates · State Management · UI]",
                    description = "The user is ordering tickets for a festival. They choose a type and a quantity, see the total update dynamically, and finalize the purchase — all within a single mobile screen.",
                    screen = Screen.TicketBuilder
                ),
                Challenge(
                    number = 3,
                    name = "Map Chip Filter",
                    topics = "[Filter Chip · State Management · UI]",
                    description = "The user sees a festival map and can toggle filters to show or hide various types of POIs (Stages, Food, WC).",
                    screen = Screen.MapChipFilter
                ),
                Challenge(
                    number = 4,
                    name = "Accessible Audio Schedule",
                    topics = "[Accessibility · Animation · UI]",
                    description = "This challenge simulates a mobile music festival app where the user browses through a scrollable list of scheduled performances.",
                    screen = Screen.AccessibleAudioSchedule
                ),
                Challenge(
                    number = 5,
                    name = "Multi-Stage Timeline Painter",
                    topics = "[Canvas · Gestures · UI]",
                    description = "Imagine this is a screen in the official app of a music festival. Users open it to view the schedule of performances across different stages. The timeline can be scrolled and zoomed for easier navigation and visibility of events.",
                    screen = Screen.MultiStageTimelinePainter
                )
            )
            ChallengeSelector(
                challenges = challenges,
                title = "September 2025\nDesigning the Festival",
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
            /*LiveTickerAggregatorRoot(
                viewModel = liveTickerAggregatorViewModel
            )*/
        }

        composable<Screen.ExpandableLineupList> {
            ExpandableLineupList()
        }
        composable<Screen.TicketBuilder> {
            TicketBuilder()
        }
        composable<Screen.MapChipFilter> {
            MapChipFilter()
        }
        composable<Screen.AccessibleAudioSchedule> {
            AccessibleAudioSchedule()
        }
        composable<Screen.MultiStageTimelinePainter> {
            MultiStageTimelinePainter()
        }
    }
}