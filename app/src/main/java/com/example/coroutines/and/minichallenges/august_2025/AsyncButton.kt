package com.example.coroutines.and.minichallenges.august_2025

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    colors: ButtonColors,
    painter: Painter?,
    applyPadding: Boolean = true,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    val mod = if (applyPadding) {
        modifier.padding(horizontal = 20.dp, vertical = 16.dp)
    } else {
        modifier
    }
    Button(
        modifier = mod,
        shape = RoundedCornerShape(12.dp),
        colors = colors,
        enabled = enable,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val contentColor = if (enable) colors.contentColor else colors.disabledContentColor
            painter?.let {
                Icon(
                    painter = painter,
                    tint = contentColor,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = stringResource(textRes),
                style = HostGroteskMedium,
                color = contentColor,
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
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Primary,
            disabledContainerColor = Surface,
            contentColor = SurfaceHigher,
            disabledContentColor = TextDisabled,
        ),
        enable = enable,
        painter = null,
        onClick = onClick
    )
}

@Composable
fun OrderQueueOutpostButton(
    modifier: Modifier = Modifier,
    isGoing: Boolean = true,
    onClick: () -> Unit
) {
    AsyncButton(
        textRes = if (isGoing) R.string.order_queue_outpost_going_button else R.string.order_queue_outpost_start_button,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = if (isGoing) SurfaceHighest else Primary,
            contentColor = if (isGoing) TextPrimary else SurfaceHigher,
        ),
        enable = true,
        painter = painterResource(if (isGoing) R.drawable.ic_pause else R.drawable.ic_play),
        onClick = onClick
    )
}

@Composable
fun ParcePigeonRaceButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    AsyncButton(
        textRes = textRes,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Primary,
            disabledContainerColor = SurfaceHighest,
            contentColor = SurfaceHigher,
            disabledContentColor = TextDisabled,
        ),
        enable = enable,
        painter = null,
        onClick = onClick
    )
}