package com.example.coroutines.and.minichallenges.august_2025.thermometer_trek

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coroutines.and.minichallenges.R
import com.example.coroutines.and.minichallenges.august_2025.thermometer_trek.Status.CAN_RESET
import com.example.coroutines.and.minichallenges.august_2025.thermometer_trek.Status.CAN_START
import com.example.coroutines.and.minichallenges.august_2025.thermometer_trek.Status.TRACKING
import com.example.coroutines.and.minichallenges.ui.theme.HostGrotesk
import com.example.coroutines.and.minichallenges.ui.theme.MiniChallengesTheme
import kotlin.math.round

@Composable
fun ThermometerTrek(
    modifier: Modifier = Modifier,
    viewModel: ThermometerTrekViewModel
) {
    when (viewModel.state.status) {
        CAN_START -> ThermometerTrekStart(
            modifier = modifier,
            onStartOrReset = {
                viewModel.startRestartThermometer()
            }
        )
        TRACKING, CAN_RESET -> ThermometerTrekTracking(
            modifier = modifier,
            state = viewModel.state,
            onStartOrReset = {
                viewModel.startRestartThermometer()
            }
        )
    }
}

@Composable
fun ThermometerTrekStart(
    modifier: Modifier = Modifier,
    onStartOrReset: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(Color(0xFFFFFFFF), RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(R.string.thermometer_trek_start_title),
            style = TextStyle(
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.sp
            ),
            color = Color(0xFF2E3642),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(R.string.thermometer_trek_start_description),
            style = TextStyle(
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp
            ),
            color = Color(0xFF66707F),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        ThermometerTrekButton(
            textRes = R.string.thermometer_trek_start_button,
            onClick = onStartOrReset
        )
    }
}

@Composable
fun ThermometerTrekTracking(
    state: ThermometerTrekState,
    modifier: Modifier = Modifier,
    onStartOrReset: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .background(Color(0xFFFFFFFF), RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(R.string.thermometer_trek_start_title),
            style = TextStyle(
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.sp
            ),
            color = Color(0xFF2E3642)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.check_circle),
                tint = if (state.status == TRACKING) Color(0xFFB4BDCA) else Color(0xFF37B98B),
                contentDescription = "Check",
                modifier = Modifier
                    .size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            val amount = "${state.temperatures.size}${stringResource(R.string.thermometer_trek_tracking_total)}"

            Text(
                text = amount,
                style = TextStyle(
                    fontFamily = HostGrotesk,
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.sp
                ),
                color = Color(0xFF66707F)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(20) { index ->
                val correctedIndex = if (index % 2 == 0) index / 2 else 10 + (index.toDouble() / 2).toInt() //index + 10 - ceil(index.toDouble() / 2).toInt()

                TemperatureItem(
                    modifier = Modifier,
                    item = state.temperatures.getOrNull(correctedIndex)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            ThermometerTrekButton(
                textRes = if (state.status == TRACKING) R.string.thermometer_trek_tracking_button else R.string.thermometer_trek_reset_button,
                enable = state.status == CAN_RESET,
                onClick = onStartOrReset
            )
        }
    }
}

@Composable
fun TemperatureItem(
    item: Double?,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.thermometer),
            tint = if (item == null) Color(0xFFB4BDCA) else Color(0xFF37B98B),
            contentDescription = "Thermometer",
            modifier = Modifier
                .size(16.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        val temperature = item?.let {
             "${(round(it * 10) / 10)} °F"
        } ?: run {
            ""
        }

        Text(
            text = temperature,
            style = TextStyle(
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp
            ),
            color = Color(0xFF2E3642)
        )
    }
}

@Composable
fun ThermometerTrekButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color(0xFF37B98B),
            disabledContainerColor = Color(0xFFF4F6F6),
            contentColor = Color(0xFFFFFFFF),
            disabledContentColor = Color(0xFFB4BDCA),
        ),
        enabled = enable,
        onClick = onClick
    ) {
        Text(
            text = stringResource(textRes),
            style = TextStyle(
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp
            ),
            color = if (enable) Color(0xFFFFFFFF) else Color(0xFFB4BDCA),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(widthDp = 400, heightDp = 900, showBackground = true)
@Composable
private fun ThermometerTrekPreview() {
    MiniChallengesTheme {
        ThermometerTrekStart(
            onStartOrReset = {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

