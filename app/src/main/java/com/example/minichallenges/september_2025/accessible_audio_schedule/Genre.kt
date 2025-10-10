package com.example.minichallenges.september_2025.accessible_audio_schedule

import androidx.compose.ui.graphics.Color
import com.example.minichallenges.september_2025.Blue
import com.example.minichallenges.september_2025.Lime
import com.example.minichallenges.september_2025.Orange
import com.example.minichallenges.september_2025.Pink
import com.example.minichallenges.september_2025.Purple

enum class Genre(val genreName: String, val color: Color) {
    INDIE("Indie", Orange),
    ELECTRONIC("Electronic", Lime),
    HEADLINER("Headliner", Purple),
    EXPERIMENTAL("Experimental", Pink),
    ALT_ROCK("Alt Rock", Blue)
}