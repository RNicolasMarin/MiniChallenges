package com.example.minichallenges.september_2025.multi_stage_timeline_painter

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minichallenges.september_2025.Lime
import com.example.minichallenges.september_2025.Orange
import com.example.minichallenges.september_2025.Outline
import com.example.minichallenges.september_2025.ParkinsansMedium
import com.example.minichallenges.september_2025.ParkinsansNormalRegular
import com.example.minichallenges.september_2025.ParkinsansSemiBold
import com.example.minichallenges.september_2025.Purple
import com.example.minichallenges.september_2025.Surface
import com.example.minichallenges.september_2025.TextPrimary
import com.example.minichallenges.september_2025.TextSecondary
import com.example.minichallenges.ui.theme.MiniChallengesTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.floor

@Composable
fun MultiStageTimelinePainter() {

    var isStarting by remember { mutableStateOf(true) }

    var showZoom by remember { mutableStateOf(false) }
    var scale by remember { mutableFloatStateOf(1f) } // 1x = 100%

    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds delay
        isStarting = false
    }

    LaunchedEffect(showZoom, scale) {
        Log.d("TAGN", "showZoom: $showZoom, scale: $scale")
        if (!showZoom) return@LaunchedEffect

        delay(2000)

        showZoom = false
    }

    val performances = listOf(
        Performance("DJ A", "Electro Stage", "12:00", "13:00"),
        Performance("Band X", "Main Stage", "13:00", "14:30"),
        Performance("RockZ", "Rock Stage", "14:00", "15:00"),
        Performance("Ambient Line", "Electro Stage", "15:00", "16:30"),
        Performance("Florence + The Machine", "Main Stage", "16:30", "18:00"),
        Performance("The National", "Rock Stage", "17:00", "18:00"),
        Performance("Jamie xx", "Electro Stage", "18:00", "19:00"),
        Performance("Tame Impala", "Main Stage", "19:00", "20:30"),
        Performance("Arctic Monkeys", "Rock Stage", "20:00", "21:30"),
        Performance("Radiohead", "Main Stage", "21:30", "23:00")
    )

    val stageOrder = listOf("Main Stage", "Rock Stage", "Electro Stage")

    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

    val sortedPerformances = performances.sortedWith(compareBy(
        { sdf.parse(it.start)?.time ?: 0L },        // sort by start time
        { stageOrder.indexOf(it.stage) }           // sort by stage
    ))

    val textMeasurer = rememberTextMeasurer()
    val hourStyle = ParkinsansNormalRegular.copy(
        color = TextSecondary,
        fontSize = 12.sp,
        lineHeight = 12.sp
    )

    val stages = listOf("Main", "Rock", "Electro")

    var titleSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Surface)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 16.dp)
                    .onGloballyPositioned {
                        titleSize = it.size
                    }
            ) {
                Text(
                    text = "Festival Schedule",
                    style = ParkinsansMedium.copy(
                        fontSize = 20.sp,
                        lineHeight = 20.sp
                    ),
                    color = TextSecondary
                )
            }


            var offset by remember { mutableStateOf(Offset.Zero) }

            val minScale = 1f
            val maxScale = 2.5f

            val verticalScrollState = rememberScrollState()
            val horizontalScrollState = rememberScrollState()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(state = horizontalScrollState, enabled = !isStarting)
                    .verticalScroll(state = verticalScrollState, enabled = !isStarting)
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            if (isStarting) return@detectTransformGestures

                            val newScale = (scale * zoom).coerceIn(minScale, maxScale)
                            val scaleChange = newScale / scale
                            if (newScale != scale) {
                                showZoom = true
                            }
                            scale = newScale

                            offset += pan * scaleChange
                        }
                    }
            ) {

                val configuration = LocalConfiguration.current
                val density = LocalDensity.current

                val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
                val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

                var canvasHeight by remember { mutableStateOf(3000.dp) }
                val canvasHeightPx = with(density) { canvasHeight.toPx() }

                // ✅ Compute actual drawable area scaled
                val contentWidth = screenWidthPx * scale
                val contentHeight = canvasHeightPx * scale

                // ✅ Compute bounds using screen size (not canvas size)
                val maxOffsetX = ((contentWidth - screenWidthPx) / 2).coerceAtLeast(0f)
                val maxOffsetY = ((contentHeight - screenHeightPx) / 2).coerceAtLeast(0f)

                // ✅ Clamp offset so you can scroll only inside content
                val clampedOffset = Offset(
                    x = offset.x.coerceIn(-maxOffsetX, maxOffsetX),
                    y = offset.y.coerceIn(-contentHeight / 2, contentHeight / 2)
                )
                offset = clampedOffset

                Canvas(
                    modifier = Modifier
                        .size(width = configuration.screenWidthDp.dp, height = canvasHeight)
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = clampedOffset.x,
                            translationY = clampedOffset.y
                        )
                ) {

                    val hours = (12..23).map {
                        val text = "$it:00"
                        val textLayoutResult = textMeasurer.measure(
                            text = AnnotatedString(text),
                            style = hourStyle
                        )

                        HourAndWidth(text, textLayoutResult.size)
                    }

                    val hoursColumnWidth = hours.maxOf { it.size.width } + 12.dp.toPx() * 2

                    val stageWidth = (size.width - hoursColumnWidth - 12.dp.toPx()) / stages.size

                    var x1 = hoursColumnWidth
                    var stageHeight = 0f

                    stages.forEachIndexed { index, stage ->
                        val textStyle = ParkinsansSemiBold.copy(
                            color = TextPrimary,
                            fontSize = 24.sp,
                            lineHeight = 24.sp
                        )
                        val textLayoutResult = textMeasurer.measure(
                            text = AnnotatedString(stage),
                            style = textStyle
                        )

                        val textWidth = textLayoutResult.size.width.toFloat()
                        val newHeight = textLayoutResult.size.height.toFloat()
                        if (newHeight > stageHeight) {
                            stageHeight = newHeight
                        }

                        // Calculate top-left offset so the text is centered
                        val textX = x1 + (stageWidth - textWidth) / 2
                        val textY = 16.dp.toPx()

                        drawText(
                            textMeasurer = textMeasurer,
                            text = stage,
                            topLeft = Offset(textX, textY),
                            style = textStyle
                        )

                        x1 += stageWidth
                    }

                    val stageTotalHeight = 16.dp.toPx() * 2 + stageHeight

                    var hoursY = stageTotalHeight + 1.dp.toPx() + 20.dp.toPx()

                    hours.forEachIndexed { index, hour ->
                        drawText(
                            textMeasurer = textMeasurer,
                            text = hour.hour,
                            topLeft = Offset(12.dp.toPx(), hoursY),
                            style = hourStyle
                        )

                        hoursY += hour.size.height + (if (index == hours.size - 1) 20.dp.toPx() else 80.dp.toPx())
                    }

                    canvasHeight = hoursY.toDp() + 10.dp

                    var gridSquareX = hoursColumnWidth
                    var gridSquareY = stageTotalHeight + 1.dp.toPx()

                    val hoursHeight = hours.first().size.height

                    stages.forEach { _ ->
                        drawRect(
                            color = Outline,
                            topLeft = Offset(gridSquareX, gridSquareY),
                            size = Size(stageWidth, 20.dp.toPx() + hoursHeight / 2),
                            style = Stroke(width = 1.dp.toPx())
                        )

                        drawRect(
                            color = Surface,
                            topLeft = Offset(gridSquareX + 1.dp.toPx(), gridSquareY + 1.dp.toPx()),
                            size = Size(stageWidth - 2.dp.toPx(), 20.dp.toPx() + hoursHeight / 2 - 2.dp.toPx()),
                        )
                        gridSquareX = gridSquareX + stageWidth
                    }
                    gridSquareX = hoursColumnWidth
                    gridSquareY = gridSquareY + 20.dp.toPx() + hoursHeight / 2


                    (0..((hours.size - 1) * 2 ) -1).forEachIndexed { index, _ ->
                        stages.forEach { _ ->
                            drawRect(
                                color = Outline,
                                topLeft = Offset(gridSquareX, gridSquareY),
                                size = Size(stageWidth, getHalfHeight(hoursHeight, index) + 40.dp.toPx()),
                                style = Stroke(width = 1.dp.toPx())
                            )

                            drawRect(
                                color = Surface,
                                topLeft = Offset(gridSquareX + 1.dp.toPx(), gridSquareY + 1.dp.toPx()),
                                size = Size(stageWidth - 2.dp.toPx(), getHalfHeight(hoursHeight, index) + 40.dp.toPx() - 2.dp.toPx()),
                            )
                            gridSquareX = gridSquareX + stageWidth
                        }
                        gridSquareX = hoursColumnWidth
                        gridSquareY = gridSquareY + getHalfHeight(hoursHeight, index) + 40.dp.toPx()
                    }

                    stages.forEach { _ ->
                        drawRect(
                            color = Outline,
                            topLeft = Offset(gridSquareX, gridSquareY),
                            size = Size(stageWidth, 20.dp.toPx() + hoursHeight / 2),
                            style = Stroke(width = 1.dp.toPx())
                        )

                        drawRect(
                            color = Surface,
                            topLeft = Offset(gridSquareX + 1.dp.toPx(), gridSquareY + 1.dp.toPx()),
                            size = Size(stageWidth - 2.dp.toPx(), 20.dp.toPx() + hoursHeight / 2 - 2.dp.toPx()),
                        )
                        gridSquareX = gridSquareX + stageWidth
                    }

                    drawRect(
                        color = TextPrimary,
                        topLeft = Offset(0f, stageTotalHeight),
                        size = Size(size.width, 1.dp.toPx()),
                    )

                    val performanceMainX = hoursColumnWidth + 4.dp.toPx()
                    val performanceRockX = hoursColumnWidth + stageWidth + 4.dp.toPx()
                    val performanceElectroX = hoursColumnWidth + stageWidth * 2 + 4.dp.toPx()
                    val performanceWidth = stageWidth - 4.dp.toPx() * 2
                    val baseY = stageTotalHeight + 1.dp.toPx() + 20.dp.toPx() + hoursHeight / 2 + 2.dp.toPx()
                    sortedPerformances.forEachIndexed { index, performance ->
                        val stage = when {
                            performance.stage.contains("Electro") -> Stage.Electro
                            performance.stage.contains("Rock") -> Stage.Rock
                            else -> Stage.Main
                        }

                        val stageX = when (stage) {
                            Stage.Main -> performanceMainX
                            Stage.Rock -> performanceRockX
                            Stage.Electro -> performanceElectroX
                        }

                        val halfHoursBefore = halfHourDifference("12:00", performance.start)
                        val completeHalfHoursBefore = floor(halfHoursBefore).toInt()

                        var stageY = baseY
                        (0..completeHalfHoursBefore).forEach {
                            if (it != 0) {
                                stageY = stageY + 40.dp.toPx() + getHalfHeight(hoursHeight, it)
                            }
                        }

                        val halfHoursDuration = halfHourDifference(performance.start, performance.end)
                        val completeHalfHoursDuration = floor(halfHoursDuration).toInt()

                        var performanceHeight = - 2.dp.toPx() * 2

                        (0..completeHalfHoursDuration).forEach {
                            if (it != 0) {
                                performanceHeight = performanceHeight + 40.dp.toPx() + getHalfHeight(hoursHeight, it)
                            }
                        }

                        drawRoundRect(
                            color = stage.color,        // Fill color
                            topLeft = Offset(stageX, stageY),
                            size = Size(performanceWidth, performanceHeight),              // Full canvas size
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(6.dp.toPx(), 6.dp.toPx()), // x and y radius
                        )

                        val measuredTextTime = textMeasurer.measure(
                            text = performance.start + "–" + performance.end,
                            style = ParkinsansNormalRegular.copy(
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                                color = TextPrimary
                            ),
                            maxLines = Int.MAX_VALUE,
                            constraints = androidx.compose.ui.unit.Constraints(maxWidth = (performanceWidth - 6.dp.toPx() * 2).toInt())
                        )

                        drawText(
                            textLayoutResult = measuredTextTime,
                            topLeft = Offset(stageX + 6.dp.toPx(), stageY + 8.dp.toPx()),
                        )

                        val measuredTextArtist = textMeasurer.measure(
                            text = performance.artist,
                            style = ParkinsansSemiBold.copy(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                color = TextPrimary
                            ),
                            maxLines = Int.MAX_VALUE,
                            constraints = androidx.compose.ui.unit.Constraints(maxWidth = (performanceWidth - 6.dp.toPx() * 2).toInt())
                        )

                        drawText(
                            textLayoutResult = measuredTextArtist,
                            topLeft = Offset(stageX + 6.dp.toPx(), stageY + 8.dp.toPx() + measuredTextTime.size.height + 6.dp.toPx()),
                        )
                    }

                    if (isStarting) {
                        drawRect(
                            color = Color(0xBF221513),
                            topLeft = Offset(0f, stageTotalHeight + 1.dp.toPx()),
                            size = Size(size.width, size.height - stageTotalHeight + 1.dp.toPx()),
                        )

                        val measuredTextZoom = textMeasurer.measure(
                            text = "Pinch to Zoom",
                            style = ParkinsansMedium.copy(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                color = Surface
                            ),
                            maxLines = Int.MAX_VALUE,
                        )

                        val screenHeight = configuration.screenHeightDp.dp.toPx()
                        val titleHeight = titleSize.height
                        val stageHeight = stageTotalHeight + 1.dp.toPx()

                        drawText(
                            textLayoutResult = measuredTextZoom,
                            topLeft = Offset(
                                (size.width - measuredTextZoom.size.width) / 2,
                                stageHeight + (screenHeight - titleHeight - stageHeight - measuredTextZoom.size.height) / 2
                            )
                        )
                    }
                }
            }
        }

        if (showZoom) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Color(0xBF221513),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(8.dp)
                ) {
                    val percentage = (scale * 100).toInt()

                    // Round to nearest 10 between 100 and 250
                    val scaleValue = percentage
                        .coerceIn(100, 250)
                        .let { it / 10 * 10 }

                    Text(
                        text = "Zoom: ${scaleValue}%",
                        style = ParkinsansNormalRegular.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp
                        ),
                        color = Surface
                    )
                }
            }
        }
    }
}

private fun getHalfHeight(hoursHeight: Int, index: Int): Int {
    return if (index % 2 == 0) {
        // Even index → round up
        (hoursHeight + 1) / 2
    } else {
        // Odd index → round down
        hoursHeight / 2
    }
}

enum class Stage(val color: Color) {
    Main(Orange),
    Rock(Purple),
    Electro(Lime)
}

data class HourAndWidth(
    val hour: String,
    val size: IntSize
)

data class Performance(
    val artist: String,
    val stage: String,
    val start: String,
    val end: String
)

fun halfHourDifference(start: String, end: String): Float {
    fun toMinutes(time: String): Int {
        val (h, m) = time.split(":").map { it.toInt() }
        return h * 60 + m
    }

    val diffMinutes = toMinutes(end) - toMinutes(start)
    return diffMinutes / 30f // each half hour = 30 minutes
}

@Preview(name = "PortraitSmall", widthDp = 300, heightDp = 672)
@Preview(name = "PortraitMedium", widthDp = 412, heightDp = 892)
@Preview(name = "PortraitLarge", widthDp = 600, heightDp = 892)
@Composable
fun MultiStageTimelinePainterPreview() {
    MiniChallengesTheme {
        MultiStageTimelinePainter()
    }
}