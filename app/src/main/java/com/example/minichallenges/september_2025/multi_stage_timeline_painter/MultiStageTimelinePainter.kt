package com.example.minichallenges.september_2025.multi_stage_timeline_painter

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minichallenges.september_2025.Outline
import com.example.minichallenges.september_2025.ParkinsansMedium
import com.example.minichallenges.september_2025.ParkinsansNormalRegular
import com.example.minichallenges.september_2025.ParkinsansSemiBold
import com.example.minichallenges.september_2025.Surface
import com.example.minichallenges.september_2025.TextPrimary
import com.example.minichallenges.september_2025.TextSecondary
import com.example.minichallenges.ui.theme.MiniChallengesTheme

@Composable
fun MultiStageTimelinePainter() {

    val textMeasurer = rememberTextMeasurer()
    val hourStyle = ParkinsansNormalRegular.copy(
        color = TextSecondary,
        fontSize = 12.sp,
        lineHeight = 12.sp
    )
    
    val stages = listOf("Main", "Rock", "Electro")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 16.dp)
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

        val verticalScrollState = rememberScrollState()
        val horizontalScrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(horizontalScrollState)
                .verticalScroll(verticalScrollState)
        ) {
            val configuration = LocalConfiguration.current

            var canvasHeight by remember { mutableStateOf(3000.dp) }
            Canvas(
                modifier = Modifier
                    .size(width = configuration.screenWidthDp.dp, height = canvasHeight)
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

                Log.d("TAGNNN", "gridSquareY: $gridSquareY")
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
                Log.d("TAGNNN", "20.dp.toPx(): ${20.dp.toPx()}")
                Log.d("TAGNNN", "hoursHeight: ${hoursHeight}")
                Log.d("TAGNNN", "hoursHeight / 2: ${hoursHeight / 2}")
                Log.d("TAGNNN", "gridSquareY: ${gridSquareY}")
                Log.d("TAGNNN", "__________")
                gridSquareY = gridSquareY + 20.dp.toPx() + hoursHeight / 2


                (0..((hours.size - 1) * 2 ) -1).forEachIndexed { index, _ ->
                    //Log.d("TAGNNN", "INDEX: $index")
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

                    /*stages.forEach { _ ->
                        drawRect(
                            color = Outline,
                            topLeft = Offset(gridSquareX, gridSquareY),
                            size = Size(stageWidth, hoursHeight / 2 + 40.dp.toPx()),
                            style = Stroke(width = 1.dp.toPx())
                        )

                        drawRect(
                            color = Surface,
                            topLeft = Offset(gridSquareX + 1.dp.toPx(), gridSquareY + 1.dp.toPx()),
                            size = Size(stageWidth - 2.dp.toPx(), hoursHeight / 2 + 40.dp.toPx() - 2.dp.toPx()),
                        )
                        gridSquareX = gridSquareX + stageWidth
                    }
                    gridSquareX = hoursColumnWidth*/
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

data class HourAndWidth(
    val hour: String,
    val size: IntSize
)

@Preview(name = "PortraitSmall", widthDp = 300, heightDp = 672)
@Preview(name = "PortraitMedium", widthDp = 412, heightDp = 892)
@Preview(name = "PortraitLarge", widthDp = 600, heightDp = 892)
@Composable
fun MultiStageTimelinePainterPreview() {
    MiniChallengesTheme {
        MultiStageTimelinePainter()
    }
}