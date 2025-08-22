package com.example.coroutines.and.minichallenges.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.coroutines.and.minichallenges.R

val HostGrotesk = FontFamily(
    Font(R.font.host_grotesk_medium, weight = FontWeight.Medium),
    Font(R.font.host_grotesk_semibold, weight = FontWeight.SemiBold),
    Font(R.font.host_grotesk_regular, weight = FontWeight.Normal),
)

val HostGroteskSemiBold = TextStyle(
    fontFamily = HostGrotesk,
    fontWeight = FontWeight.SemiBold,
    fontSize = 28.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp
)

val HostGroteskNormalRegular = TextStyle(
    fontFamily = HostGrotesk,
    fontWeight = FontWeight.Normal,
    fontSize = 17.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp
)

val HostGroteskMedium = TextStyle(
    fontFamily = HostGrotesk,
    fontWeight = FontWeight.Medium,
    fontSize = 17.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)