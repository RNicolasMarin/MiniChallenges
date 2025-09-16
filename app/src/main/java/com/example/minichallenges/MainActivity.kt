package com.example.minichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.minichallenges.august_2025.Surface
import com.example.minichallenges.august_2025.live_ticker_aggregator.LiveTickerAggregator
import com.example.minichallenges.august_2025.live_ticker_aggregator.LiveTickerAggregatorViewModel
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
                    val challenges = listOf(
                        Challenge(
                            number = 1,
                            nameRes = R.string.thermometer_trek_challenge_name,
                            topicsRes = R.string.thermometer_trek_challenge_topics,
                            descriptionRes = R.string.thermometer_trek_challenge_description
                        ),
                        Challenge(
                            number = 2,
                            nameRes = R.string.order_queue_outpost_challenge_name,
                            topicsRes = R.string.order_queue_outpost_challenge_topics,
                            descriptionRes = R.string.order_queue_outpost_challenge_description
                        ),
                        Challenge(
                            number = 3,
                            nameRes = R.string.parce_pigeon_race_challenge_name,
                            topicsRes = R.string.parce_pigeon_race_challenge_topics,
                            descriptionRes = R.string.parce_pigeon_race_challenge_description
                        ),
                        Challenge(
                            number = 4,
                            nameRes = R.string.heartbeat_sentinel_challenge_name,
                            topicsRes = R.string.heartbeat_sentinel_challenge_topics,
                            descriptionRes = R.string.heartbeat_sentinel_challenge_description
                        ),
                        Challenge(
                            number = 5,
                            nameRes = R.string.live_ticker_aggregator_challenge_name,
                            topicsRes = R.string.live_ticker_aggregator_challenge_topics,
                            descriptionRes = R.string.live_ticker_aggregator_challenge_description
                        )
                    )
                    ChallengeSelector(
                        challenges = challenges,
                        title = R.string.mini_challenges_august_2025
                    )
                    /*Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Surface)
                            .padding(top = 20.dp)
                    ) {
                        val viewmodel by viewModels<LiveTickerAggregatorViewModel>()
                        LiveTickerAggregator(
                            viewModel = viewmodel,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }*/
                    /*ThousandsSeparatorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFEF7FF))
                            .padding(16.dp)
                    )*/
                    /*var percentages = listOf(7, 19, 25, 55, 75, 80, 85)
                    var percentageIndex by rememberSaveable {
                        mutableIntStateOf(0)
                    }
                    LaunchedEffect(true) {
                        while (true) {
                            delay(3000)
                            percentageIndex++
                            if (percentageIndex == percentages.size) {
                                percentageIndex = 0
                            }
                        }
                    }

                    BatteryIndicatorUI(
                        percentages[percentageIndex],
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE7E9EF))
                            .padding(32.dp)
                    )*/
                    /*BatteryIndicatorUI(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE7E9EF))
                            .padding(32.dp)
                    )*/
                }
            }
        }
    }
}