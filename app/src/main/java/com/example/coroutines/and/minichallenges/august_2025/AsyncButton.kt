package com.example.coroutines.and.minichallenges.august_2025

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.coroutines.and.minichallenges.R
import com.example.coroutines.and.minichallenges.ui.theme.HostGroteskMedium

@Composable
fun AsyncButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    disabledContainerColor: Color,
    disabledContentColor: Color,
    painter: Painter?,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Primary,
            disabledContainerColor = disabledContainerColor,
            contentColor = SurfaceHigher,
            disabledContentColor = disabledContentColor,
        ),
        enabled = enable,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            painter?.let {
                Icon(
                    painter = painter,
                    tint = if (enable) SurfaceHigher else Primary,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = stringResource(textRes),
                style = HostGroteskMedium,
                color = if (enable) SurfaceHigher else TextDisabled,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ThermometerTrekButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    AsyncButton(
        textRes = textRes,
        modifier = modifier,
        disabledContainerColor = Surface,
        disabledContentColor = TextDisabled,
        enable = enable,
        painter = null,
        onClick = onClick
    )
}

@Composable
fun OrderQueueOutpostButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    AsyncButton(
        textRes = textRes,
        modifier = modifier,
        disabledContainerColor = SurfaceHighest,
        disabledContentColor = TextPrimary,
        enable = enable,
        painter = painterResource(if (enable) R.drawable.ic_play else R.drawable.ic_pause),
        onClick = onClick
    )
}