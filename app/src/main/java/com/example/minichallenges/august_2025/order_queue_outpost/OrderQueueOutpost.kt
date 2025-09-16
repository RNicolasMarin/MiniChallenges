package com.example.minichallenges.august_2025.order_queue_outpost

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minichallenges.R
import com.example.minichallenges.august_2025.Error
import com.example.minichallenges.august_2025.ErrorClear
import com.example.minichallenges.august_2025.OrderQueueOutpostButton
import com.example.minichallenges.august_2025.Overload
import com.example.minichallenges.august_2025.Primary
import com.example.minichallenges.august_2025.Surface
import com.example.minichallenges.august_2025.SurfaceHigher
import com.example.minichallenges.august_2025.SurfaceHighest
import com.example.minichallenges.august_2025.TextDisabled
import com.example.minichallenges.august_2025.TextPrimary
import com.example.minichallenges.august_2025.TextSecondary
import com.example.minichallenges.august_2025.Warning
import com.example.minichallenges.august_2025.order_queue_outpost.Status.CAN_START
import com.example.minichallenges.august_2025.order_queue_outpost.Status.GOING
import com.example.minichallenges.august_2025.order_queue_outpost.Status.PAUSED
import com.example.minichallenges.ui.theme.HostGroteskMedium
import com.example.minichallenges.ui.theme.HostGroteskNormalRegular
import com.example.minichallenges.ui.theme.HostGroteskSemiBold
import com.example.minichallenges.ui.theme.MiniChallengesTheme

@Composable
fun OrderQueueOutpostRoot(
    viewModel: OrderQueueOutpostViewModel = OrderQueueOutpostViewModel()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .padding(26.dp)
    ) {
        OrderQueueOutpost(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

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
        PAUSED, GOING -> OrderQueueOutpostGoingOrPaused(
            state = viewModel.state,
            modifier = modifier,
            onStartOrReset = {
                viewModel.stopOrRestartConsuming()
            }
        )
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

@Composable
fun OrderQueueOutpostGoingOrPaused(
    state: OrderQueueOutpostState,
    modifier: Modifier = Modifier,
    onStartOrReset: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(SurfaceHigher, RoundedCornerShape(16.dp))
            .padding(vertical = 24.dp, horizontal = 20.dp)
    ) {
        val percentage = state.orderAmount * 100 / 25

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ){
            Text(
                text = stringResource(R.string.order_queue_outpost_start_title),
                style = HostGroteskSemiBold,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.order_queue_outpost_paused_going_queue, state.orderAmount),
                    style = HostGroteskMedium.copy(
                        fontSize = 16.sp,
                        lineHeight = 18.sp
                    ),
                    color = TextPrimary
                )

                Text(
                    text = stringResource(R.string.order_queue_outpost_paused_going_percentage, percentage),
                    style = HostGroteskNormalRegular.copy(
                        fontSize = 16.sp,
                        lineHeight = 18.sp
                    ),
                    color = if (percentage > 100) Overload else TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        val density = LocalDensity.current
        val infiniteTransition = rememberInfiniteTransition(label = "borderValue")

        val animatedValue by infiniteTransition.animateFloat(
            initialValue = with(density) { 0.dp.toPx() },
            targetValue = with(density) { 2.dp.toPx() },
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "borderValue"
        )

        val measurer = rememberTextMeasurer()

        Canvas(modifier = Modifier.fillMaxWidth()) {
            val canvasWidthPx = size.width
            val withCornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            val withoutCornerRadius = CornerRadius(0.dp.toPx(), 0.dp.toPx())

            if (percentage > 100) {
                val path = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(
                                offset = Offset(animatedValue, animatedValue),
                                size = Size(canvasWidthPx - animatedValue * 2, 20.dp.toPx() - animatedValue * 2)
                            ),
                            topLeft = withCornerRadius,
                            topRight = withCornerRadius,
                            bottomRight = withCornerRadius,
                            bottomLeft = withCornerRadius
                        )
                    )
                }

                drawPath(
                    path = path,
                    color = ErrorClear
                )
            }

            val chartWidthPx = canvasWidthPx - 8.dp.toPx()

            val spaceWidthPx = 1.dp.toPx()

            val amountSpaces = if (percentage > 100) 3 else 2
            val totalPercentage = if (percentage > 100) percentage else 100

            val onePercentageWidthPx = (chartWidthPx - amountSpaces * spaceWidthPx) / totalPercentage
            val spacePair = Pair(spaceWidthPx, null)

            val generalFilledColor = when {
                percentage <= 33 -> Primary
                percentage <= 67 -> Warning
                else -> Error
            }

            val createPairOrNull = { currentPercentage: Int?, color: Color ->
                currentPercentage?.let {
                    Pair(onePercentageWidthPx * it, color)
                }
            }

            val segmentOneFilledPercentage = when {
                percentage == 0 -> null
                percentage < 33 -> percentage
                else -> 33
            }

            val segmentOneEmptyPercentage = when {
                percentage < 33 -> 33 - percentage
                else -> null
            }

            val segmentTwoFilledPercentage = when {
                percentage > 33 && percentage < 67 -> percentage - 33
                percentage > 33 -> 34
                else -> null
            }

            val segmentTwoEmptyPercentage = when {
                percentage > 33 && percentage < 67 -> 67 - percentage
                percentage < 67 -> 34
                else -> null
            }

            val segmentThreeFilledPercentage = when {
                percentage > 67 && percentage < 100 -> percentage - 67
                percentage > 67 -> 33
                else -> null
            }

            val segmentThreeEmptyPercentage = when {
                percentage > 67 && percentage < 100 -> 100 - percentage
                percentage < 100 -> 33
                else -> null
            }

            val segmentFourFilledPercentage = when {
                percentage > 100 -> percentage - 100
                else -> null
            }

            val segmentOneFilled = createPairOrNull(segmentOneFilledPercentage, generalFilledColor)
            val segmentOneEmpty = createPairOrNull(segmentOneEmptyPercentage, SurfaceHighest)
            val segmentTwoFilled = createPairOrNull(segmentTwoFilledPercentage, generalFilledColor)
            val segmentTwoEmpty = createPairOrNull(segmentTwoEmptyPercentage, SurfaceHighest)
            val segmentThreeFilled = createPairOrNull(segmentThreeFilledPercentage, generalFilledColor)
            val segmentThreeEmpty = createPairOrNull(segmentThreeEmptyPercentage, SurfaceHighest)
            val thirdSpace = if (percentage > 100) spacePair else null
            val segmentFourFilled = createPairOrNull(segmentFourFilledPercentage, Overload)

            val eachSectionWidthPx2 = listOfNotNull(
                segmentOneFilled,
                segmentOneEmpty,
                spacePair,
                segmentTwoFilled,
                segmentTwoEmpty,
                spacePair,
                segmentThreeFilled,
                segmentThreeEmpty,
                thirdSpace,
                segmentFourFilled
            )

            val topY = 4.dp.toPx()
            var leftX = 4.dp.toPx()

            var textX = 0f

            eachSectionWidthPx2.forEachIndexed { index, item ->
                val leftCorners = if (index == 0) withCornerRadius else withoutCornerRadius
                val useRightCorners = index == eachSectionWidthPx2.size - 1
                val rightCorners = if (useRightCorners) withCornerRadius else withoutCornerRadius

                item.second?.let {
                    val path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(
                                    offset = Offset(leftX, topY),
                                    size = Size(item.first, 12.dp.toPx())
                                ),
                                topLeft = leftCorners,
                                topRight = rightCorners,
                                bottomRight = rightCorners,
                                bottomLeft = leftCorners
                            )
                        )
                    }

                    drawPath(
                        path = path,
                        color = it
                    )
                }

                if (thirdSpace != null && index == eachSectionWidthPx2.size - 2) {
                    textX = leftX + item.first / 2
                }

                leftX = leftX + item.first
            }


            val newTextStyle = HostGroteskMedium.copy(
                fontSize = 13.sp,
                lineHeight = 16.sp,
                color = TextDisabled,
                textAlign = TextAlign.Center
            )

            val valueResult = measurer.measure(
                text = if (thirdSpace != null) "100%" else "",
                style = newTextStyle,
                maxLines = 1
            )

            val textY = 22.dp.toPx()
            textX = textX - valueResult.size.width / 2

            drawText(
                topLeft = Offset(textX, textY),
                textLayoutResult = valueResult,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OrderQueueOutpostButton(
            isGoing = state.status == GOING,
            onClick = onStartOrReset
        )
    }
}

@Preview(widthDp = 400, heightDp = 900, showBackground = true)
@Composable
private fun ThermometerTrekPreview() {
    MiniChallengesTheme {
        OrderQueueOutpostGoingOrPaused(
            state = OrderQueueOutpostState(
                status = GOING,
                orderAmount = 26
            ),
            onStartOrReset = {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}