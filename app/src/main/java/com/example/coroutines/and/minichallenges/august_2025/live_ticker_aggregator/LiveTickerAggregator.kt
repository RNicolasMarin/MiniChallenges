package com.example.coroutines.and.minichallenges.august_2025.live_ticker_aggregator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.coroutines.and.minichallenges.R
import com.example.coroutines.and.minichallenges.august_2025.AsyncButton
import com.example.coroutines.and.minichallenges.august_2025.Primary
import com.example.coroutines.and.minichallenges.august_2025.Surface
import com.example.coroutines.and.minichallenges.august_2025.SurfaceHigher
import com.example.coroutines.and.minichallenges.august_2025.SurfaceHighest
import com.example.coroutines.and.minichallenges.august_2025.TextDisabled
import com.example.coroutines.and.minichallenges.august_2025.TextPrimary
import com.example.coroutines.and.minichallenges.august_2025.TextSecondary
import com.example.coroutines.and.minichallenges.august_2025.live_ticker_aggregator.LiveTickerAggregatorAction.*
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskMedium
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskNormalRegular
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskSemiBold
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LiveTickerAggregator(
    modifier: Modifier = Modifier,
    viewModel: LiveTickerAggregatorViewModel
) {
    val state by viewModel.state.collectAsState()
    LiveTickerAggregator(
        state = state,
        modifier = modifier,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun LiveTickerAggregator(
    state: LiveTickerAggregatorState,
    onAction: (LiveTickerAggregatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.live_ticker_aggregator_title),
                        style = HostGroteskSemiBold,
                        color = TextPrimary
                    )

                    if (!state.isRunning) {
                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            painter = painterResource(R.drawable.ic_pause_text),
                            tint = TextDisabled,
                            contentDescription = "Icon",
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(color = SurfaceHighest, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {

                    val tickRate = if (state.isRunning) {
                        stringResource(R.string.live_ticker_aggregator_rate, (1000 / state.updatesRate).toInt())
                    } else {
                        stringResource(R.string.live_ticker_aggregator_rate_stopped)
                    }

                    Text(
                        text = tickRate,
                        style = HostGroteskMedium,
                        color = TextSecondary,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items(
                items = state.exchanges,
                key = { exchange -> exchange.name }
            ) { exchange ->
                LiveTickerAggregatorExchangeItem(
                    exchange = exchange
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SurfaceHigher, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(16.dp)
        ) {
            AsyncButton(
                textRes = if (state.isRunning) R.string.live_ticker_aggregator_pause else R.string.live_ticker_aggregator_resume,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = SurfaceHighest,
                    contentColor = TextPrimary,
                ),
                applyPadding = false,
                enable = true,
                painter = painterResource(if (state.isRunning) R.drawable.ic_pause_2 else R.drawable.ic_play_2),
                onClick = {
                    onAction(OnPauseResume)
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

            val xetraAvailable = true

            AsyncButton(
                textRes = R.string.live_ticker_aggregator_break,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = if (xetraAvailable) Primary else Surface,
                    contentColor = if (xetraAvailable) SurfaceHigher else TextDisabled,
                ),
                applyPadding = false,
                enable = true,
                painter = null,
                onClick = {}
            )
        }
    }
}

@Composable
fun LiveTickerAggregatorExchangeItem(
    exchange: Exchange,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceHigher),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = exchange.name,
                    style = HostGroteskMedium,
                    color = TextPrimary,
                )

                Spacer(modifier = Modifier.height(5.dp))

                val date = Date(exchange.lastTimeUpdated)
                val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val time = formatter.format(date)

                Text(
                    text = time,
                    style = HostGroteskNormalRegular,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    text = "${exchange.currentPrice} USD",
                    style = HostGroteskMedium,
                    color = TextPrimary,
                )
            }
        }
    }
}