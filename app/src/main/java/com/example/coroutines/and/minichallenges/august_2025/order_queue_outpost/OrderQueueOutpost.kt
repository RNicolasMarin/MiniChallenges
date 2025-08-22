package com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.coroutines.and.minichallenges.R
import com.example.coroutines.and.minichallenges.august_2025.OrderQueueOutpostButton
import com.example.coroutines.and.minichallenges.august_2025.SurfaceHigher
import com.example.coroutines.and.minichallenges.august_2025.TextPrimary
import com.example.coroutines.and.minichallenges.august_2025.TextSecondary
import com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost.Status.CAN_START
import com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost.Status.GOING
import com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost.Status.PAUSED
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskNormalRegular
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskSemiBold

@Composable
fun OrderQueueOutpost(
    modifier: Modifier = Modifier,
    viewModel: OrderQueueOutpostViewModel
) {
    when (viewModel.state.status) {
        CAN_START -> OrderQueueOutpostStart(
            state = viewModel.state,
            modifier = modifier,
            onStartOrReset = {
                viewModel.startProducer()
            }
        )
        PAUSED -> {}
        GOING -> {}
    }
}

@Composable
fun OrderQueueOutpostStart(
    state: OrderQueueOutpostState,
    modifier: Modifier = Modifier,
    onStartOrReset: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(SurfaceHigher, RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(R.string.order_queue_outpost_start_title),
            style = HostGroteskSemiBold,
            color = TextPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(R.string.order_queue_outpost_start_description),
            style = HostGroteskNormalRegular,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        OrderQueueOutpostButton(
            isGoing = state.status == GOING,
            onClick = onStartOrReset
        )
    }
}