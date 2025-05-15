package com.example.coroutines.and.minichallenges.february_2025.thousands_separator_picker

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coroutines.and.minichallenges.ui.theme.MiniChallengesTheme

@Composable
fun ThousandsSeparatorPicker(
    modifier: Modifier = Modifier,
    text: String = "Thousands separator",//In case the component could be used with other values
    options: List<String> = listOf("1.000", "1,000", "1 000"),
    onClickOption: (Int) -> Unit = {}
) {
    var selectedOption by remember { mutableIntStateOf(0) }

    var textHeightDp by remember { mutableStateOf(0.dp) }
    var textWidthDp by remember { mutableStateOf(0.dp) }

    val animatedOffset by animateDpAsState(
        targetValue = (textWidthDp) * (selectedOption),
        label = "IndicatorOffset"
    )

    val density = LocalDensity.current

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(500),
            color = Color(0xFF1B1B1C),
            text = text
        )
        Spacer(
            modifier = Modifier.height(4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF4E8FF))
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = animatedOffset)
                    .size(textWidthDp, textHeightDp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFFFFF))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                options.forEachIndexed { index, option ->
                    ThousandsSeparatorNumber(
                        number = options[index],
                        selected = selectedOption == index,
                        onClick = {
                            onClickOption(index)
                            selectedOption = index
                        },
                        modifier = Modifier
                            .weight(1f)
                            .then(
                                if (textHeightDp == 0.dp && textWidthDp == 0.dp) {
                                    Modifier.onGloballyPositioned { coordinates ->
                                        textHeightDp = with(density) { coordinates.size.height.toDp() }
                                        textWidthDp = with(density) { coordinates.size.width.toDp() }
                                    }
                                } else {
                                    Modifier
                                }
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun ThousandsSeparatorNumber(
    number: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = number,
        fontWeight = FontWeight(600),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center,
        color = if (selected) Color(0xFF1B1B1C) else Color(0xFF24005A),
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    )
}

@Preview(widthDp = 300, heightDp = 100, showBackground = true)
@Composable
private fun ThousandsSeparatorPickerPreview() {
    MiniChallengesTheme {
        ThousandsSeparatorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFEF7FF))
                .padding(16.dp)
        )
    }
}