package com.example.coroutines.and.minichallenges.august_2025.thermometer_trek

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun ThermometerTrek(
    modifier: Modifier = Modifier,
    viewModel: ThermometerTrekViewModel = ThermometerTrekViewModel(ThermometerTrekRepository())
) {
    when (viewModel.state.status) {
        CAN_START -> ThermometerTrekStart(
            modifier = modifier,
            onStartOrReset = {
                viewModel.startThermometer()
            }
        )
        TRACKING -> Unit
        CAN_RESET -> Unit
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
fun ThermometerTrekButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            //.background(Color(0xFF37B98B), RoundedCornerShape(12.dp))
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
            color = Color(0xFFFFFFFF),
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
                .background(Color(0xFFF4F6F6))
                .padding(24.dp)
        )
    }
}

