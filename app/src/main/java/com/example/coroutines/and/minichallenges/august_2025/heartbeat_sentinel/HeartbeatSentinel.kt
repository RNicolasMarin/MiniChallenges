package com.example.coroutines.and.minichallenges.august_2025.heartbeat_sentinel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coroutines.and.minichallenges.R
import com.example.coroutines.and.minichallenges.august_2025.Error
import com.example.coroutines.and.minichallenges.august_2025.Primary
import com.example.coroutines.and.minichallenges.august_2025.SurfaceHigher
import com.example.coroutines.and.minichallenges.august_2025.TextPrimary
import com.example.coroutines.and.minichallenges.august_2025.TextSecondary
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskMedium
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskNormalRegular
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskSemiBold
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun HeartbeatSentinel(
    modifier: Modifier = Modifier,
    viewModel: HeartbeatSentinelViewModel
) {
    HeartbeatSentinel(
        state = viewModel.state,
        modifier = modifier
    )
}

@Composable
fun HeartbeatSentinel(
    state: HeartbeatSentinelState,
    modifier: Modifier = Modifier)
{
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.heartbeat_sentinel_title),
                style = HostGroteskSemiBold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            var stationOffline by remember { mutableStateOf<Pair<Int, String>?>(null) }

            val firstStationOffline = state.getFirstOnline()

            LaunchedEffect(firstStationOffline?.status) {
                if (firstStationOffline == null) {
                    stationOffline = null
                } else {
                    val station = when (firstStationOffline.station) {
                        Station.A -> R.string.heartbeat_sentinel_station_a
                        Station.B -> R.string.heartbeat_sentinel_station_b
                        Station.C -> R.string.heartbeat_sentinel_station_c
                    }
                    while (true) {
                        val time = ((System.currentTimeMillis() - firstStationOffline.lastOnLine).toDouble() / 1000.0 * 10).toInt() / 10.0
                        stationOffline = Pair(station, time.toString())
                        delay(1.milliseconds)
                    }
                }
            }

            val text = stationOffline?.let {
                stringResource(R.string.heartbeat_sentinel_offline, stringResource(it.first), it.second)
            } ?: run {
                stringResource(R.string.heartbeat_sentinel_operational)
            }

            Text(
                text = text,
                style = HostGroteskNormalRegular,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                HeartbeatSentinelStation(
                    modifier = Modifier.weight(1f),
                    textRes = R.string.heartbeat_sentinel_station_a,
                    color = state.stationA.getColor()
                )

                Spacer(modifier = Modifier.width(8.dp))

                HeartbeatSentinelStation(
                    modifier = Modifier.weight(1f),
                    textRes = R.string.heartbeat_sentinel_station_b,
                    color = state.stationB.getColor()
                )

                Spacer(modifier = Modifier.width(8.dp))

                HeartbeatSentinelStation(
                    modifier = Modifier.weight(1f),
                    textRes = R.string.heartbeat_sentinel_station_c,
                    color = state.stationC.getColor()
                )
            }
        }
    }
}

fun HeartbeatSentinelStateStation.getColor(): Color {
    return when (this.status) {
        is StationStatus.NotShowing -> SurfaceHigher
        is StationStatus.Showing -> Primary
        StationStatus.Offline -> Error
    }
}

@Composable
fun HeartbeatSentinelStation(
    textRes: Int,
    color: Color,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceHigher),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .aspectRatio(1f)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_station),
                tint = color,
                contentDescription = stringResource(textRes),
                modifier = Modifier
                    .size(20.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(textRes),
                style = HostGroteskMedium.copy(
                    fontSize = 19.sp,
                    lineHeight = 24.sp,
                ),
                color = TextPrimary
            )
        }
    }
}