package com.example.coroutines.and.minichallenges.february_2025.battery_indicator_ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coroutines.and.minichallenges.R
import com.example.coroutines.and.minichallenges.ui.theme.MiniChallengesTheme

/*
Show a heart ♥️ and clover 🍀 icon at both ends of the battery level indicator

* */

@Composable
fun BatteryIndicatorUI(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.heart),
            contentDescription = "Heart",
            tint = Red,
            modifier = Modifier.size(48.dp),
        )

        Spacer(Modifier.width(16.dp))

        Canvas(modifier = Modifier.weight(1f).height(68.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val path1 = Path().apply {
                drawRoundRect(
                    color = White,
                    size = Size(canvasWidth - 5.dp.toPx(), canvasHeight),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )

                drawRoundRect(
                    topLeft = Offset(canvasWidth - 10.dp.toPx(), canvasHeight * 0.5f -12.dp.toPx()),
                    color = White,
                    size = Size(10.dp.toPx(), 25.dp.toPx()),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
            }
            drawPath(
                path = path1,
                color = Red
            )
        }

        Spacer(Modifier.width(16.dp))

        Icon(
            painter = painterResource(R.drawable.clover),
            contentDescription = "Clover",
            tint = Grey,
            modifier = Modifier.size(48.dp),
        )
    }
}

@Preview(widthDp = 400, heightDp = 900, showBackground = true)
@Composable
private fun ThousandsSeparatorPickerPreview() {
    MiniChallengesTheme {
        BatteryIndicatorUI(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE7E9EF))
        )
    }
}

/*
Feature Goal
Create a UI component that indicates the device's battery level with a unique animation for when the battery level is low and one for when the battery is sufficiently charged.

Requirements
Observe the device's battery level and feed the information to the UI component

The icons change based on the battery level percentage %





<= 20%





Heart starts pulsing on repeat continuously



As the heart scales up, it gets more tinted with a white color



Clover remains greyed out and scaled-down



>= 80%





The clover scales up slightly



The heart remains greyed out and scaled down



> 20% and < 80%





Both icons are greyed and scaled-down as per the mockups



The bar fills using an animation so that the bar appears to extend or shrink as the battery level changes



The bar color animates between these colours, each fully matching its color at the target % value





20% - Red - #FF4E51



50% - Yellow - #FCB966



80% - Green - #19D181



Note: This is NOT a purely UI challenge; the battery level percentage must display the real battery % of the device it runs on.

Tip: In your Compose @Preview function, you can animate a state variable to simulate the battery %





🏆 Submission & Rewards





A successful submission of this challenge via the /submit-challenge command on Discord grants you 75 XP. You can use it in any channel on Discord :)



A successful submission consists of these parts





A link to a Gist showing your code for this challenge.



A screen recording (20s max) that shows each of the battery states. You can simulate this via the Android emulator.
* */