package com.example.minichallenges.september_2025

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.minichallenges.R

val Parkinsans = FontFamily(
    Font(R.font.parkinsans_medium, weight = FontWeight.Medium),
    Font(R.font.parkinsans_semibold, weight = FontWeight.SemiBold),
    Font(R.font.parkinsans_regular, weight = FontWeight.Normal),
)

val ParkinsansSemiBold = TextStyle(
    fontFamily = Parkinsans,
    fontWeight = FontWeight.SemiBold,
    fontSize = 60.sp,
    lineHeight = 54.sp,
    letterSpacing = 0.sp
)

val ParkinsansNormalRegular = TextStyle(
    fontFamily = Parkinsans,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp
)

val ParkinsansMedium = TextStyle(
    fontFamily = Parkinsans,
    fontWeight = FontWeight.Medium,
    fontSize = 24.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)
