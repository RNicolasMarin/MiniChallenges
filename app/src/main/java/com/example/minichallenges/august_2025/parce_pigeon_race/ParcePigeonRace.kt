package com.example.minichallenges.august_2025.parce_pigeon_race

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.minichallenges.R
import com.example.minichallenges.august_2025.ParcePigeonRaceButton
import com.example.minichallenges.august_2025.SurfaceHigher
import com.example.minichallenges.august_2025.TextPrimary
import com.example.minichallenges.august_2025.TextSecondary
import com.example.minichallenges.august_2025.ThermometerTrekButton
import com.example.minichallenges.august_2025.parce_pigeon_race.Status.*
import com.example.minichallenges.ui.theme.HostGroteskMedium
import com.example.minichallenges.ui.theme.HostGroteskNormalRegular
import com.example.minichallenges.ui.theme.HostGroteskSemiBold
import java.text.DecimalFormat

@Composable
fun ParcePigeonRace(
    modifier: Modifier = Modifier,
    viewModel: ParcePigeonRaceViewModel
) {
    val cacheDir = LocalContext.current.cacheDir
    when (viewModel.state.status) {
        CAN_RUN -> ParcePigeonRaceCanRun(
            modifier = modifier,
            onRunOrRerun = {
                viewModel.runOrRunAgain(cacheDir)
            }
        )
        RUNNING, CAN_RUN_AGAIN -> {
            ParcePigeonRaceRunningOrRunAgain(
                state = viewModel.state,
                modifier = modifier,
                onRunAgain = {
                    viewModel.runOrRunAgain(cacheDir)
                }
            )
        }
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

@Composable
fun ParcePigeonRaceRunningOrRunAgain(
    state: ParcePigeonRaceState,
    modifier: Modifier = Modifier,
    onRunAgain: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(scrollState),
    ) {
        Text(
            text = stringResource(R.string.parce_pigeon_race_can_run_title),
            style = HostGroteskSemiBold,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        state.images.forEach {
            PigeonImageUi(it)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.parce_pigeon_race_running_run_again_total_time),
                style = HostGroteskMedium.copy(
                    fontSize = 19.sp,
                    lineHeight = 22.sp,
                ),
                color = TextPrimary,
            )
            Spacer(modifier = Modifier.weight(1f))

            val longestTime = state.images.maxOf { it.durationInMilSeconds }
            val totalTime = when {
                longestTime == 0L -> stringResource(R.string.parce_pigeon_race_running_run_again_no_time)
                else -> {
                    stringResource(R.string.parce_pigeon_race_running_run_again_time, longestTime / 1000)
                }
            }
            Text(
                text = totalTime,
                style = HostGroteskNormalRegular.copy(
                    fontSize = 16.sp,
                    lineHeight = 18.sp,
                ),
                color = TextPrimary,
            )
            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        ParcePigeonRaceButton(
            textRes = R.string.parce_pigeon_race_running_run_again_button,
            enable = state.images.all { !it.isLoading },
            onClick = onRunAgain
        )
    }
}

@Composable
fun PigeonImageUi(
    pigeonImage: PigeonImage,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(SurfaceHigher, RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier.size(60.dp)
        ) {
            when {
                pigeonImage.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                pigeonImage.file != null -> AsyncImage(
                    model = pigeonImage.file,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Inside
                )
                else -> Unit
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.parce_pigeon_race_running_run_again_image, pigeonImage.position + 1),
                style = HostGroteskMedium.copy(
                    fontSize = 19.sp,
                    lineHeight = 22.sp,
                ),
                color = TextPrimary,
            )

            Spacer(modifier = Modifier.height(5.dp))

            val size = when {
                pigeonImage.isLoading -> stringResource(R.string.parce_pigeon_race_running_run_again_fetching)
                else -> "${DecimalFormat("#.##").format(pigeonImage.size)}MB"
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = size,
                    style = HostGroteskNormalRegular.copy(
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                    ),
                    color = TextSecondary,
                )

                if (!pigeonImage.isLoading) {
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(R.string.parce_pigeon_race_running_run_again_time, pigeonImage.durationInMilSeconds / 1000),
                        style = HostGroteskNormalRegular.copy(
                            fontSize = 16.sp,
                            lineHeight = 18.sp,
                        ),
                        color = TextPrimary,
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}