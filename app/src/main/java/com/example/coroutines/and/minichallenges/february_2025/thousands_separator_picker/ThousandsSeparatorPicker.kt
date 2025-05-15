package com.example.coroutines.and.minichallenges.february_2025.thousands_separator_picker

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coroutines.and.minichallenges.ui.theme.MiniChallengesTheme

/*
Already Done

Include a title text: "Thousands separator"
Horizontal bar with three different options and rounded corners
The option's text color changes based on whether its selected or not
* */

@Composable
fun ThousandsSeparatorPicker(
    modifier: Modifier = Modifier
) {
    val text = "Thousands separator"
    val option1 = "1.000"
    val option2 = "1,000"
    val option3 = "1 000"
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF4E8FF),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(4.dp)
        ) {
            ThousandsSeparatorNumber(
                number = option1,
                selected = true,
                modifier = Modifier.weight(1f)
            )
            ThousandsSeparatorNumber(
                number = option2,
                selected = false,
                modifier = Modifier.weight(1f)
            )
            ThousandsSeparatorNumber(
                number = option3,
                selected = false,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ThousandsSeparatorNumber(
    number: String,
    selected: Boolean,
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
            .then(
                if (selected)
                    Modifier.background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(12.dp)
                    )
                else Modifier
            )
            .padding(vertical = 8.dp)
    )
}

@Preview(widthDp = 300, heightDp = 100, showBackground = true)
@Composable
private fun ThousandsSeparatorPickerPreview() {
    MiniChallengesTheme {
        ThousandsSeparatorPicker(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/*
A simple custom UI component where the user can select one of a few options
Create a UI component that highlights one of the available options and updates its selection to the option tapped.

Requirements

The component always has one of the options selected, which is indicated by a white rounded rectangle

Optional Extension - animate the change in selection

Animate the white to move to the newly selected item

🏆 Submission & Rewards

A successful submission of this challenge via the /submit-challenge command on Discord grants you 50 XP. You can use it in any channel on Discord :)

A successful submission consists of these parts

A link to a Gist showing your code for this challenge.

A screen recording (10s max) that shows selecting the 3rd, then 2nd and then 1st thousands separator.*/