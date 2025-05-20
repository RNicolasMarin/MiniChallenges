package com.example.coroutines.and.minichallenges.february_2025.battery_indicator_ui

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.coroutines.and.minichallenges.R
import com.example.coroutines.and.minichallenges.ui.theme.MiniChallengesTheme

@Composable
fun BatteryIndicatorUI(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var batteryLevel by remember { mutableIntStateOf(-1) }

    DisposableEffect(Unit) {
        val receiver = context.observeBatteryLevel { newLevel ->
            if (batteryLevel != newLevel) {

                batteryLevel = newLevel
            }
        }
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    BatteryIndicatorUI(
        percentage = batteryLevel,
        modifier = modifier
    )
}

fun Context.observeBatteryLevel(onLevelChanged: (Int) -> Unit): BroadcastReceiver {
    val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            if (scale > 0) {
                val percent = level * 100 / scale
                onLevelChanged(percent)
            }
        }
    }

    val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // API 33+
        registerReceiver(batteryReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
    } else {
        // < API 33
        @Suppress("DEPRECATION")
        registerReceiver(batteryReceiver, filter)
    }

    return batteryReceiver
}

@Composable
fun BatteryIndicatorUI(
    percentage: Int,
    modifier: Modifier = Modifier
) {
    var percentages by remember { mutableStateOf(
        Percentages(
            previous = percentage,
            current = percentage
        ))
    }
    if (percentages.current != percentage) {
        percentages = Percentages(
            previous = percentages.current,
            current = percentage
        )
    }

    val animatedPercentages by animateIntAsState(
        targetValue = percentages.current,
        animationSpec = tween(1000),
        label = "PercentagesAnimation"
    )

    val animatedBatteryColor by animateColorAsState(
        targetValue = when {
            percentages.current <= 20 -> Red
            percentages.current >= 80 -> Green
            else -> Yellow
        },
        animationSpec = tween(durationMillis = 1000),
        label = "colorTransition"
    )

    val batterySegments = divideIntoChunks(animatedPercentages, 20)
    val spaceAroundBatteryDp = 16.dp
    val iconsSizeDp = 48.dp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        var alphaTargetHeartInactive = when {
            percentages.previous > 20 && percentages.current > 20 -> 1f //was and still is inactive

            percentages.previous <= 20 && percentages.current <= 20 -> 0f //was and still is active

            percentages.previous > 20 && percentages.current <= 20 -> 0f //was inactive and now is active

            else -> 1f //was active and now is inactive
        }

        var alphaTargetHeartSmall = when {
            percentages.previous > 20 && percentages.current > 20 -> 0f //was and still is inactive

            percentages.previous <= 20 && percentages.current <= 20 -> 1f //was and still is active

            percentages.previous > 20 && percentages.current <= 20 -> 1f //was inactive and now is active

            else -> 0f //was active and now is inactive
        }

        val alphaAnimatedHeartInactive by animateFloatAsState(
            targetValue = alphaTargetHeartInactive,
            animationSpec = tween(1000),
            label = "AlphaHeartInactiveAnimation"
        )

        val alphaAnimatedHeartActive by animateFloatAsState(
            targetValue = 1f - alphaTargetHeartInactive,
            animationSpec = tween(1000),
            label = "AlphaHeartActiveAnimation"
        )

        val transition = rememberInfiniteTransition(label = "AlphaTransitionHeartActive")

        val alphaAnimatedHeartSmall by transition.animateFloat(
            initialValue = alphaTargetHeartSmall,
            targetValue = 1f - alphaTargetHeartSmall,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Reverse
            ), label = "AlphaHeartSmallAnimation"
        )

        val alphaAnimatedHeartBig by transition.animateFloat(
            initialValue = 1f - alphaTargetHeartSmall,
            targetValue = alphaTargetHeartSmall,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Reverse
            ), label = "AlphaHeartBigAnimation"
        )

        Box(modifier = Modifier.size(iconsSizeDp)) {
            Box(modifier = Modifier.size(iconsSizeDp).graphicsLayer(alpha = alphaAnimatedHeartActive)) {
                Icon(
                    painter = painterResource(id = R.drawable.heart_small),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(alpha = alphaAnimatedHeartSmall)
                )
                Icon(
                    painter = painterResource(id = R.drawable.heart_big),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(alpha = alphaAnimatedHeartBig)
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.heart_inactive),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = alphaAnimatedHeartInactive)
            )
        }

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
                color = animatedBatteryColor
            )
        }

        Spacer(Modifier.width(spaceAroundBatteryDp))

        var alphaTargetCloverInactive = when {
            percentages.previous < 80 && percentages.current < 80 -> 1f //was and still is inactive

            percentages.previous >= 80 && percentages.current >= 80 -> 0f //was and still is active

            percentages.previous < 80 && percentages.current >= 80 -> 0f //was inactive and now is active

            else -> 1f //was active and now is inactive
        }

        val alphaAnimatedCloverInactive by animateFloatAsState(
            targetValue = alphaTargetCloverInactive,
            animationSpec = tween(1000),
            label = "AlphaCloverInactiveAnimation"
        )

        val alphaAnimatedCloverActive by animateFloatAsState(
            targetValue = 1f - alphaTargetCloverInactive,
            animationSpec = tween(1000),
            label = "AlphaCloverActiveAnimation"
        )

        Box(modifier = Modifier.size(iconsSizeDp)) {
            Icon(
                painter = painterResource(id = R.drawable.clover_inactive),
                contentDescription = "CloverInactive",
                tint = Color.Unspecified,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = alphaAnimatedCloverInactive)
            )
            Icon(
                painter = painterResource(id = R.drawable.clover_active),
                contentDescription = "CloverActive",
                tint = Color.Unspecified,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = alphaAnimatedCloverActive)
            )
        }
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

data class Percentages(
    var previous: Int,
    var current: Int
)

@Preview(widthDp = 400, heightDp = 900, showBackground = true)
@Composable
private fun ThousandsSeparatorPickerPreview() {
    MiniChallengesTheme {
        BatteryIndicatorUI(
            percentage = 20,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE7E9EF))
        )
    }
}