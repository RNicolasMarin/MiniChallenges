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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
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
    percentage: Int,
    modifier: Modifier = Modifier
) {
    val batterySegments = divideIntoChunks(percentage, 20)
    val spaceAroundBatteryDp = 16.dp
    val iconsSizeDp = 48.dp
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.heart),
            contentDescription = "Heart",
            tint = Red,
            modifier = Modifier.size(iconsSizeDp),
        )

        Spacer(Modifier.width(spaceAroundBatteryDp))

        Canvas(modifier = Modifier.weight(1f).height(68.dp)) {
            val canvasWidthPx = size.width
            val canvasHeightPx = size.height

            val batteryEndWidthPx = 10.dp.toPx()
            val batteryEndHeightPx = 25.dp.toPx()
            
            val batteryPaddingPx = 4.dp.toPx()
            val spaceBetweenPx = 2.dp.toPx()

            val batteryBackgroundWidthPx = canvasWidthPx - batteryEndWidthPx / 2
            val batterySegmentWidthPx = (batteryBackgroundWidthPx - batteryPaddingPx * 2 - spaceBetweenPx * 4) / 5
            val batterySegmentHeightPx = canvasHeightPx - batteryPaddingPx * 2

            val withCornerRadius = CornerRadius(16.dp.toPx())
            val withoutCornerRadius = CornerRadius(0.dp.toPx())

            val path = Path().apply {

                drawRoundRect(
                    color = White,
                    size = Size(batteryBackgroundWidthPx, canvasHeightPx),
                    cornerRadius = withCornerRadius
                )

                drawRoundRect(
                    topLeft = Offset(canvasWidthPx - batteryEndWidthPx, canvasHeightPx * 0.5f - batteryEndHeightPx / 2),
                    color = White,
                    size = Size(batteryEndWidthPx, batteryEndHeightPx),
                    cornerRadius = withCornerRadius
                )

                (0..batterySegments.size - 1).map {

                    val portionOfSegmentWidthPx = if (it == batterySegments.size - 1) batterySegments[it] * 0.05f else 1f

                    val startXPx = batteryPaddingPx +
                            batterySegmentWidthPx * it +
                            spaceBetweenPx * it

                    val leftCorners = if (it == 0) withCornerRadius else withoutCornerRadius
                    val rightCorners = if (it == batterySegments.size - 1) withCornerRadius else withoutCornerRadius

                    addRoundRect(
                        RoundRect(
                            rect = Rect(
                                offset = Offset(startXPx, batteryPaddingPx),
                                size = Size(batterySegmentWidthPx * portionOfSegmentWidthPx, batterySegmentHeightPx)
                            ),
                            topLeft = leftCorners,
                            topRight = rightCorners,
                            bottomRight = rightCorners,
                            bottomLeft = leftCorners
                        )
                    )
                }
            }
            drawPath(
                path = path,
                color = Red
            )
        }

        Spacer(Modifier.width(spaceAroundBatteryDp))

        Icon(
            painter = painterResource(R.drawable.clover),
            contentDescription = "Clover",
            tint = Grey,
            modifier = Modifier.size(iconsSizeDp),
        )
    }
}

fun divideIntoChunks(total: Int, chunkSize: Int): List<Int> {
    val fullChunks = total / chunkSize
    val remainder = total % chunkSize

    val result = MutableList(fullChunks) { chunkSize }
    if (remainder > 0) {
        result.add(remainder)
    }

    return result
}

@Preview(widthDp = 400, heightDp = 900, showBackground = true)
@Composable
private fun ThousandsSeparatorPickerPreview() {
    MiniChallengesTheme {
        BatteryIndicatorUI(
            percentage = 100,
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