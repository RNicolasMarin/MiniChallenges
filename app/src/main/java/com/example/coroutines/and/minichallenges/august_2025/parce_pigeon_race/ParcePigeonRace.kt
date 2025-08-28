package com.example.coroutines.and.minichallenges.august_2025.parce_pigeon_race

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
import com.example.coroutines.and.minichallenges.august_2025.SurfaceHigher
import com.example.coroutines.and.minichallenges.august_2025.TextPrimary
import com.example.coroutines.and.minichallenges.august_2025.TextSecondary
import com.example.coroutines.and.minichallenges.august_2025.ThermometerTrekButton
import com.example.coroutines.and.minichallenges.august_2025.parce_pigeon_race.Status.*
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskNormalRegular
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskSemiBold

@Composable
fun ParcePigeonRace(
    modifier: Modifier = Modifier,
    viewModel: ParcePigeonRaceViewModel
) {
    when (viewModel.state.status) {
        CAN_RUN -> ParcePigeonRaceCanRun(
            modifier = modifier,
            onRunOrRerun = {
                //viewModel.startProducer()
            }
        )
        RUNNING -> {

        }
        CAN_RUN_AGAIN -> {

        }
        /*PAUSED, GOING -> OrderQueueOutpostGoingOrPaused(
            state = viewModel.state,
            modifier = modifier,
            onStartOrReset = {
                viewModel.stopOrRestartConsuming()
            }
        )*/
    }
}

@Composable
fun ParcePigeonRaceCanRun(
    modifier: Modifier = Modifier,
    onRunOrRerun: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(SurfaceHigher, RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(R.string.parce_pigeon_race_can_run_title),
            style = HostGroteskSemiBold,
            color = TextPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(R.string.parce_pigeon_race_can_run_description),
            style = HostGroteskNormalRegular,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        ThermometerTrekButton(
            textRes = R.string.parce_pigeon_race_can_run_button,
            onClick = onRunOrRerun
        )
    }
}