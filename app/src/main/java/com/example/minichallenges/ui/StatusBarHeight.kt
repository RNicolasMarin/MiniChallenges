package com.example.minichallenges.ui

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars

@Composable
fun statusBarHeight(): Dp {
    val insets = WindowInsets.statusBars.asPaddingValues()
    return insets.calculateTopPadding()
}

@Composable
fun bottomBarHeight(): Dp {
    val insets = WindowInsets.navigationBars.asPaddingValues()
    return insets.calculateBottomPadding()
}